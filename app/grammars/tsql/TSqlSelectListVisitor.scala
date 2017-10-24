package grammars.tsql

import grammars.Schema
import scala.collection.JavaConverters._

class TSqlSelectListVisitor extends TSqlParserBaseVisitor[Schema] {
  override def visitSelect_list(ctx: TSqlParser.Select_listContext): Schema = {
    ctx.select_list_elem.asScala.map(visitSelect_list_elem).head
  }

  override def visitSelect_list_elem(ctx: TSqlParser.Select_list_elemContext): Schema = {
    println(ctx.getText)
    val sch = new Schema(Seq[String](ctx.getText))
    sch
  }
}
