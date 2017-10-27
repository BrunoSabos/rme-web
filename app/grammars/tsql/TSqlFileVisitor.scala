package grammars.tsql

import grammars.Schema

class TSqlFileVisitor(var fileId: String) extends TSqlParserBaseVisitor[Schema] {
  var schema: Schema = new Schema(fileId)

  def getSchema(parser: TSqlParser): Schema = {
    // todo hum
    schema.parser = parser
    visitTsql_file(parser.tsql_file())

    var derivedTablesNames = schema.tables.filter(_.derived).map(_.name)

    schema.log(s"Remove relations with derived tables")
    schema.relations = schema.relations.filter(r => !derivedTablesNames.contains(r.table1) && !derivedTablesNames.contains(r.table2))

    schema.log(s"Remove derived tables "+derivedTablesNames.mkString(", "))
    schema.tables = schema.tables.filter(!_.derived)
    schema
  }

//  override def visitTsql_file(ctx: TSqlParser.Tsql_fileContext): Schema = {
//    visitChildren(ctx)
//    schema
//  }

  override def visitQuery_specification (ctx: TSqlParser.Query_specificationContext): Schema = {
//    visitChildren(ctx)
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
//    schema.addTable()
    if(ctx.table_type_definition() != null && ctx.LOCAL_ID() != null)
    {
      val tableName = ctx.LOCAL_ID().getText
      schema.log(s"\t+ add table $tableName")

      var table = schema.addTable(tableName)
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
