package grammars.tsql

import grammars.Schema

class TSqlFileVisitor(var fileId: String) extends TSqlParserBaseVisitor[Schema] {
  var schema: Schema = new Schema(fileId)

  def getSchema(parser: TSqlParser): Schema = {
    visitTsql_file(parser.tsql_file())
  }

  override def visitTsql_file(ctx: TSqlParser.Tsql_fileContext): Schema = {
    val vis = new TSqlQuerySpecificationVisitor(schema)
    vis.visit(
    ctx.batch(0).
      sql_clauses().
      sql_clause(0).
      dml_clause().
      select_statement().
      query_expression().
      query_specification()
    )
    schema
  }
}
