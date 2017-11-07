package grammars.tsql

import grammars.Schema
import scala.collection.JavaConverters._

class TSqlQuerySpecificationVisitor(val schema: Schema) extends TSqlParserBaseVisitor[Schema] {

  override def visitQuery_specification (ctx: TSqlParser.Query_specificationContext): Schema = {
    if (ctx.table_sources() == null){
      return schema
    }

    val vis = new TSqlSelectListVisitor(schema)
    vis.visit(ctx.table_sources())
    vis.visit(ctx.select_list())
    ctx.search_condition().asScala.map(vis.visit)
    ctx.group_by_item().asScala.map(vis.visit)
    schema
  }
}
