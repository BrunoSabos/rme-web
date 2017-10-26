package grammars.tsql

import grammars.tsql.TSqlParser.Full_column_nameContext
import grammars.{Column, Schema, Table, Trace}
import org.antlr.v4.runtime.ParserRuleContext

import scala.collection.JavaConverters._

class TSqlSelectListVisitor(val schema: Schema) extends TSqlParserBaseVisitor[Schema] {

  def addTableTrace(table: Table, ctx: ParserRuleContext): Unit = {
    println(s"\t\t+ trace for table ${table.name}, ${ctx.start.getLine}:${ctx.start.getCharPositionInLine}")
    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
  }

  def addColumnTrace(column: Column, ctx: ParserRuleContext): Unit = {
    println(s"\t\t+ trace for column ${column.name}, ${ctx.start.getLine}:${ctx.start.getCharPositionInLine}")
    column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
  }

  var alias: String = ""

  override def visitTable_source_item (ctx: TSqlParser.Table_source_itemContext): Schema =  {
    if(ctx.as_table_alias != null) {
      alias = ctx.as_table_alias.table_alias.id.getText
    }
    println(s"= Table alias $alias")
    visitChildren(ctx)
    alias = ""
    schema
  }

  override def visitFull_table_name (ctx: TSqlParser.Full_table_nameContext): Schema = {
    println("FullTableName")
    println(s"\t+ table ${ctx.table.getText} ($alias)")
    var table = schema.addTable(ctx.table.getText)
    if (!alias.isEmpty) {
      schema.addScope(alias, ctx.table.getText)
    }
    else if(schema.fromScope(ctx.table.getText) isDefined)
    {
      table = schema.addTable(schema.fromScope(ctx.table.getText).get)
    }
    addTableTrace(table, ctx)
    visitChildren(ctx)
    schema
  }

  override def visitTable_name (ctx: TSqlParser.Table_nameContext): Schema = {
    println(s"TableName")
    println(s"\t+ table ${ctx.table.getText} ($alias)")
    var table = schema.addTable(ctx.table.getText)
    if (!alias.isEmpty) {
      schema.addScope(alias, ctx.table.getText)
    }
    else if(schema.fromScope(ctx.table.getText) isDefined)
    {
      table = schema.addTable(schema.fromScope(ctx.table.getText).get)
    }
    addTableTrace(table, ctx)
    visitChildren(ctx)
    schema
  }

  override def visitFull_column_name (ctx: TSqlParser.Full_column_nameContext): Schema = {
    println("FullColumnElem")
    var table: Table = null

    var alias: String = ""
    if (ctx.table_name != null) {
      alias = ctx.table_name.getText
      if (schema.fromScope(alias).isDefined) {
        table = schema.addTable(schema.fromScope(alias).get)
      }
      else {
        table = schema.addTable(alias)
      }

      println(s"\t+ column ${ctx.column_name.getText} in ${table.name}")

      val column = table.addColumn(ctx.column_name.getText)

      addTableTrace(table, ctx)
      addColumnTrace(column, ctx)
      //    visitChildren(ctx)
    } else {
      println(s"\t? column ${ctx.column_name.getText}, no table found")
    }
    schema
  }

  override def visitColumn_elem(ctx: TSqlParser.Column_elemContext): Schema = {
    println("ColumnElem")

    val tableName = ctx.table_name()
    if(tableName != null) {
      var table: Table = null
      var alias: String = tableName.getText
      if (schema.fromScope(alias).isDefined) {
        table = schema.addTable(schema.fromScope(alias).get)
      }
      else
      {
        table = schema.addTable(alias)
      }
      println(s"\t+ column ${ctx.column_name.getText} with table hint ${table.name}")

      val column = table.addColumn(ctx.column_name.getText)

      addTableTrace(table, ctx)
      addColumnTrace(column, ctx)
      return schema
    }
    // todo scope
    if(schema.tables.length == 1)
    {
      var table = schema.tables.head
      println(s"\t+ column ${ctx.column_name.getText} without table hint ${table.name}")

      val column = table.addColumn(ctx.column_name.getText)

      addTableTrace(table, ctx)
      addColumnTrace(column, ctx)
    }
    visitChildren(ctx)
    schema
  }

  override def visitPredicate (ctx: TSqlParser.PredicateContext): Schema = {
    visitChildren(ctx)
    if(ctx.comparison_operator() != null) {
      // todo other operators
      if(ctx.comparison_operator().start.getType == TSqlLexer.EQUAL && ctx.expression().asScala.length == 2) {
        val a = ctx.expression(0)
        val b = ctx.expression(1)
        //val ruleA = a.getRuleIndex
        //val aName = schema.parser.getRuleNames()(ruleA)
//        println(s"\t+ relation ? ${a.children.asScala.head.getClass}")
        var table1: String = null
        var field1: String = null
        var table2: String = null
        var field2: String = null
        a.children.asScala.head match {
          case aI: Full_column_nameContext =>
            if (aI.table_name() != null) {
              // todo refact
              table1 = aI.table_name().getText
              if(schema.fromScope(table1) isDefined) {
                table1 = schema.addTable(schema.fromScope(table1).get).name
              }
            }
            if (aI.id() != null) {
              field1 = aI.id().getText
            }
          case _ =>
        }
        b.children.asScala.head match {
          case aI: Full_column_nameContext =>
            if (aI.table_name() != null) {
              table2 = aI.table_name().getText
              if(schema.fromScope(table2) isDefined) {
                table2 = schema.addTable(schema.fromScope(table2).get).name
              }
            }
            if (aI.id() != null) {
              field2 = aI.id().getText
            }
          case _ =>
        }

        if(table1 != null && field1 != null && table2 != null && field2 != null) {
          println(s"\t+ relation between ${table1}, ${field1}, ${table2}, ${field2}")
          schema.addRelation(table1, field1, table2, field2)
        }
      }
    }
    schema
  }
}
