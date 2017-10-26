package grammars.tsql

import grammars.Schema

class TSqlFileVisitor(var fileId: String) extends TSqlParserBaseVisitor[Schema] {
  var schema: Schema = new Schema(fileId)

  def getSchema(parser: TSqlParser): Schema = {
    // todo hum
    schema.parser = parser
    visitTsql_file(parser.tsql_file())
    schema
  }

//  override def visitTsql_file(ctx: TSqlParser.Tsql_fileContext): Schema = {
//    visitChildren(ctx)
//    schema
//  }

  override def visitQuery_specification (ctx: TSqlParser.Query_specificationContext): Schema = {
//    visitChildren(ctx)
    val vis = new TSqlQuerySpecificationVisitor(schema)
    vis.visit(ctx)
    schema
  }
}
