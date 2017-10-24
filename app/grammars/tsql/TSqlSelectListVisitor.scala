package grammars.tsql

import grammars.{Schema, Table, Trace}

import scala.collection.JavaConverters._

class TSqlSelectListVisitor(val schema: Schema) extends TSqlParserBaseVisitor[Schema] {

  var alias: String = ""
  override def visitTable_source_item (ctx: TSqlParser.Table_source_itemContext): Schema =  {
    if(ctx.as_table_alias != null) {
      alias = ctx.as_table_alias.table_alias.getText
    }
    visitChildren(ctx)
    alias = ""
    schema
  }

  override def visitFull_table_name (ctx: TSqlParser.Full_table_nameContext): Schema = {
    println("FullTableName add table "+ctx.getText+ " ("+alias+")")
    var table = schema.addTable(ctx.getText)
    if (!alias.isEmpty) {
      schema.addScope(alias, ctx.getText)
    }
    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    visitChildren(ctx)
    schema
  }

  override def visitTable_name (ctx: TSqlParser.Table_nameContext): Schema = {
    println("TableName add table "+ctx.getText+ " ("+alias+")")
    val table = schema.addTable(ctx.getText)
    if (!alias.isEmpty) {
      schema.addScope(alias, ctx.getText)
    }
    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    visitChildren(ctx)
    schema
  }

  override def visitFull_column_name (ctx: TSqlParser.Full_column_nameContext): Schema = {
    var table: Table = null

    val alias = ctx.table_name.getText
    if (schema.fromScope(alias).isDefined) {
      table = schema.addTable(schema.fromScope(alias).get)
    }
    else
    {
      table = schema.addTable(alias)
    }
    println("FullColumnName add column "+table.name + " : " + ctx.column_name.getText)

    val column = table.addColumn(ctx.column_name.getText)

//    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    visitChildren(ctx)
    column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    schema
  }

//  override def visitTable_source_item_joined (ctx: TSqlParser.Table_source_item_joinedContext): Schema = {
//    visitChildren(ctx)
//  }

//  override def visitSelect_list(ctx: TSqlParser.Select_listContext): Schema = {
//    ctx.select_list_elem.asScala.map(visitSelect_list_elem).head
//  }

  override def visitColumn_elem(ctx: TSqlParser.Column_elemContext): Schema = {
    println("ColumnElem add column ? "+ctx.getText)

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
      println("ColumnElem add column OK "+table.name + " : "+ctx.column_name.getText)

      val column = table.addColumn(ctx.column_name.getText)

      table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
      column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
      return schema
    }
    // todo scope
    if(schema.tables.length == 1)
    {
      var table = schema.tables.head
      println("ColumnElem add column OK "+table.name + " : "+ctx.column_name.getText)

      val column = table.addColumn(ctx.column_name.getText)

      table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
      column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    }
    visitChildren(ctx)
    schema
  }
}
