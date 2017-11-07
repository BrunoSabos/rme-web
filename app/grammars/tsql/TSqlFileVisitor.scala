package grammars.tsql

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import grammars.{Column, Field, Relation, Schema}
import org.antlr.v4.runtime._
import org.antlr.v4.runtime.misc.ParseCancellationException
import services.{ParseError, ThrowingErrorListener}

import scala.collection._

class TSqlFileVisitor(var fileId: String) extends TSqlParserBaseVisitor[Schema] {
  var schema: Schema = new Schema(fileId)
  var parseErrors: Seq[ParseError] = Seq[ParseError]()


  def getErrors: Seq[ParseError] = {
    parseErrors
  }

  def getSchema(statements: String, logEnabled: Boolean = true): Schema = {
    schema.logEnabled = logEnabled
    val errorListener = new ThrowingErrorListener()
    val statementsUpper = statements.toUpperCase
    val stream = new ByteArrayInputStream(statementsUpper.getBytes(StandardCharsets.UTF_8))

    try {
      val lexer = new TSqlLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8))
      lexer.removeErrorListeners()
      lexer.addErrorListener(errorListener)

      val tokens = new CommonTokenStream(lexer)
      val parser: TSqlParser = new grammars.tsql.TSqlParser(tokens)
      parser.removeErrorListeners()
      parser.addErrorListener(errorListener)

      visitTsql_file(parser.tsql_file())

      // todo hum
      schema.parser = parser
      val derivedTablesNames = schema.tables.filter(_.derived).map(_.name)

      schema.log(s"Relations: ${schema.relations.length}")
      schema.log(s"Bind temp tables relations")

      var count: Int = 0
      var recur: Int = 0
      do {
        schema.log(s"Add relations from temp tables")
        count = 0
        val sortedRelations = schema.relations
        //.getSortedRelations
        val relationsOk = sortedRelations.filter(r => isTableTemp(r.table1) ^ isTableTemp(r.table2))
        var relationsOkTempTotal: Seq[Relation] = Seq[Relation]()

        relationsOk.map(r => if (isTableTemp(r.table1)) r else new Relation(r.table2, r.field2, r.table1, r.field1)).groupBy(r => r.table1 + "." + r.field1).foreach(g => {
          val r1 = g._2.head
          schema.log(s"Relation: ${r1.table1}.${r1.field1}")
          g._2.foreach(r2 => {
            schema.log(s"\t# -> _: ${r2.table1}.${r2.field1} -> ${r2.table2}.${r2.field2}")
          })

          val relationsOkTemp = sortedRelations.filter(r => isTableTemp(r.table1) && isTableTemp(r.table2) &&
            (
              r.table1 == r1.table1 && r.field1 == r1.field1
                | r.table2 == r1.table1 && r.field2 == r1.field1
              ))
          relationsOkTempTotal = relationsOkTempTotal ++ relationsOkTemp
          var tempRelations = relationsOkTemp.map(r => if (r.table1 == r1.table1 && r.field1 == r1.field1) r else new Relation(r.table2, r.field2, r.table1, r.field1))

          tempRelations.foreach(rt => {
            schema.log(s"\t# -> #: ${rt.table1}.${rt.field1} -> ${rt.table2}.${rt.field2}")
          })

          if (g._2.length + tempRelations.length > 1) {
            (g._2 ++ tempRelations).map(d => new Field(d.table2, d.field2)).combinations(2).foreach(c => {
              schema.log(s"\tadd _ -> _: ${c.head.table}.${c.head.column} -> ${c.last.table}.${c.last.column}")
              schema.relations = schema.relations :+ new Relation(c.head.table, c.head.column, c.last.table, c.last.column)
              count += 1
            })
          }
        })
        schema.log(s"Relations before: ${schema.relations.length}")
        schema.log(s"Relations ok: ${relationsOk.length}")
        schema.log(s"Relations temp: ${relationsOkTempTotal.length}")
        schema.relations = schema.relations.diff(relationsOk ++ relationsOkTempTotal)

        schema.log(s"Relations diff: ${schema.relations.length}")

        schema.relations = schema.getFlippedRelations.groupBy(r => (r.table1, r.field1, r.table2, r.field2)).map(g => g._2.head).toSeq
        schema.log(s"Relations distinct: ${schema.relations.length}")

        recur += 1
      } while (count > 0 && recur < 5)
      if(recur == 5) {
        println("RECUR, abort")
        schema.log("RECUR, abort")
        return null
      }

      schema.relations = schema.getFlippedRelations.groupBy(r => (r.table1, r.field1, r.table2, r.field2)).map(g => g._2.head).toSeq
      schema.log(s"Relations distinct: ${schema.relations.length}")

      schema.log(s"Remove relations with derived tables")
      schema.relations = schema.relations.filter(r => !derivedTablesNames.contains(r.table1) && !derivedTablesNames.contains(r.table2))

      schema.log(s"Remove derived tables " + derivedTablesNames.mkString(", "))
      schema.tables = schema.tables.filter(!_.derived)

      schema.log(s"Remove empty and temp tables")
      schema.tables = schema.tables.filter(t => t.columns.nonEmpty && !isTableTemp(t.name))

      schema.log(s"Remove orphan relations")
      schema.relations = schema.relations.filter(r => schema.tables.exists(r.table1 == _.name) && schema.tables.exists(r.table2 == _.name))
    } catch {
      case _: ParseCancellationException =>
        parseErrors = errorListener.getErrors
        return null
    }
    schema
  }

  def isTableTemp(table: String): Boolean = {
    table.startsWith("#") || table.startsWith("@")
  }

  override def visitQuery_specification(ctx: TSqlParser.Query_specificationContext): Schema = {
    schema.log(s"> Push scope")
    schema.openScope()

    val vis = new TSqlQuerySpecificationVisitor(schema)
    vis.visit(ctx)

    schema.log(s"< Pop scope")
    schema.closeScope()

    schema
  }

  override def visitDeclare_statement(ctx: TSqlParser.Declare_statementContext): Schema = {
    schema.log(s"Declare")
    if (ctx.table_type_definition() != null && ctx.LOCAL_ID() != null) {
      val tableName = ctx.LOCAL_ID().getText
      schema.log(s"\t+ add table $tableName")

      val table = schema.addTable(tableName)
      ctx.table_type_definition().column_def_table_constraints().column_def_table_constraint().forEach(c => {
        val id = c.column_definition().id(0).getText
        schema.log(s"\t\t+ add column $id")
        table.addColumn(id)
      })
    }
    visitChildren(ctx)
    schema
  }
}
