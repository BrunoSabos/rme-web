package grammars.tsql

import grammars.{Schema, Trace}

class TSqlQuerySpecificationVisitor(val schema: Schema) extends TSqlParserBaseVisitor[Schema] {

  override def visitQuery_specification (ctx: TSqlParser.Query_specificationContext): Schema = {
    schema.openScope()
    val vis = new TSqlSelectListVisitor(schema)
    vis.visit(ctx.table_sources())
    vis.visit(ctx.select_list())
    schema.closeScope()
  }
}
