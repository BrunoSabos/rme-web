package grammars.tsql

import grammars.{Schema, Trace}

import scala.collection.JavaConverters._

class TSqlSelectListVisitor(val schema: Schema) extends TSqlParserBaseVisitor[Schema] {

  override def visitTable_source_item (ctx: TSqlParser.Table_source_itemContext): Schema = {
    println("TableSourceItem add table "+ctx.getText)
    val table = schema.addTable(ctx.getText)
    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    visitChildren(ctx)
    schema
  }

  override def visitFull_column_name (ctx: TSqlParser.Full_column_nameContext): Schema = {
    println("FullColumnName add column "+ctx.table_name.getText + " : " + ctx.column_name.getText)
    val table = schema.addTable(ctx.table_name.getText)
    val column = table.addColumn(ctx.column_name.getText)

    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
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
      println("ColumnElem add column OK "+tableName.getText + " : "+ctx.column_name.getText)

      val table = schema.addTable(tableName.getText)
      val column = table.addColumn(ctx.column_name.getText)

      table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
      column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    }
    // todo scope
    if(schema.tables.length == 1)
    {
      println("ColumnElem add column OK "+schema.tables.head.name + " : "+ctx.column_name.getText)

      val column = schema.tables.head.addColumn(ctx.column_name.getText)

      column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    }
    visitChildren(ctx)
    schema
  }
}
