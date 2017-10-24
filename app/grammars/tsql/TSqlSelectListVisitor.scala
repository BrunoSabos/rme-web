package grammars.tsql

import grammars.{Schema, Trace}

import scala.collection.JavaConverters._

class TSqlSelectListVisitor(var schema: Schema) extends TSqlParserBaseVisitor[Schema] {

  override def visitTable_source_item_joined (ctx: TSqlParser.Table_source_item_joinedContext): Schema = {
    var table = schema.addTable(ctx.getText)
    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
    visitChildren(ctx)
  }

//  override def visitSelect_list(ctx: TSqlParser.Select_listContext): Schema = {
//    ctx.select_list_elem.asScala.map(visitSelect_list_elem).head
//  }

  override def visitSelect_list_elem(ctx: TSqlParser.Select_list_elemContext): Schema = {
    println(ctx.getText)
    var table = schema.tables.head
    var column = table.addColumn(ctx.getText)
    column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
//    val sch = new Schema(Seq[String](ctx.getText))
    schema
  }
}
