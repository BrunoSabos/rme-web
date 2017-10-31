package grammars.tsql

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import grammars.Schema
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

  def getSchema(statements: String): Schema = {
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

      schema.log(s"Remove relations with derived tables")
      schema.relations = schema.relations.filter(r => !derivedTablesNames.contains(r.table1) && !derivedTablesNames.contains(r.table2))

      schema.log(s"Remove derived tables "+derivedTablesNames.mkString(", "))
      schema.tables = schema.tables.filter(!_.derived)
    } catch {
      case _: ParseCancellationException =>
        parseErrors = errorListener.getErrors
        return null
    }
    schema
  }

  override def visitQuery_specification (ctx: TSqlParser.Query_specificationContext): Schema = {
    schema.log(s"> Push scope")
    schema.openScope()

    val vis = new TSqlQuerySpecificationVisitor(schema)
    vis.visit(ctx)

    schema.log(s"< Pop scope")
    schema.closeScope()

    schema
  }

  override def visitDeclare_statement (ctx: TSqlParser.Declare_statementContext): Schema = {
    schema.log(s"Declare")
    if(ctx.table_type_definition() != null && ctx.LOCAL_ID() != null)
    {
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
