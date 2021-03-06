package grammars.tsql

import grammars.tsql.TSqlParser.{Full_column_nameContext, IdContext}
import grammars.{Column, Schema, Table, Trace}
import org.antlr.v4.runtime.ParserRuleContext

import scala.collection.JavaConverters._

class TSqlSelectListVisitor(val schema: Schema) extends TSqlParserBaseVisitor[Schema] {

  var noHintTable: Option[String] = Option.empty[String]

  def addTableTrace(table: Table, ctx: ParserRuleContext): Unit = {
    schema.log(s"\t\t+ trace for table ${table.name}, ${ctx.start.getLine}:${ctx.start.getCharPositionInLine}")
    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
  }

  def addColumnTrace(column: Column, ctx: ParserRuleContext): Unit = {
    schema.log(s"\t\t+ trace for column ${column.name}, ${ctx.start.getLine}:${ctx.start.getCharPositionInLine}")
    column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
  }

  override def visitExpression_elem(ctx: TSqlParser.Expression_elemContext): Schema = {
    // todo column alias
    if (ctx.expression().bracket_expression() != null) {
      val subQueryVisitor = new TSqlQuerySpecificationVisitor(schema)
      subQueryVisitor.visit(ctx.expression())
    } else {
      visitChildren(ctx)
    }
    schema
  }

  override def visitTable_source_item(ctx: TSqlParser.Table_source_itemContext): Schema = {
    if (ctx.derived_table() != null) {
      val tableName = getCleanId(ctx.as_table_alias.table_alias.id)

      schema.derivedTable = tableName +: schema.derivedTable
      schema.log(s"+ Derived table ${schema.derivedTable.head} +: ${schema.derivedTable.tail.mkString(",")}")
      schema.addTable(tableName, derived = true)
      schema.log(s"> Push scope")
      schema.openScope()

      val subQueryVisitor = new TSqlQuerySpecificationVisitor(schema)
      subQueryVisitor.visit(ctx)
      val lastColumnScope = schema.columnScopes.head

      schema.log(s"< Pop scope")
      schema.closeScope()

      schema.log(s"> Columns aliases extraction from derived tables without table name: " + lastColumnScope.map(e => s"${e._1} -> ${schema.derivedTable.head} (${e._2.table}).${e._2.column}").mkString(", "))

      lastColumnScope.foreach(e => schema.addColumnScope(e._1, e._2.table, e._2.column, siblingsOnly = true))


      schema.log(s"> Columns aliases extraction from derived tables with table name: " + lastColumnScope.map(e => s"${e._1} -> ${schema.derivedTable.head} (${e._2.table}).${e._2.column}").mkString(", "))

      lastColumnScope.foreach(e => schema.addColumnScope(schema.derivedTable.head + "." + e._1, e._2.table, e._2.column, siblingsOnly = true))

      schema.log(s"- Derived table ${schema.derivedTable.head} -: ${schema.derivedTable.tail.mkString(",")}")
      schema.derivedTable = schema.derivedTable.tail
    }
    else {
      if (ctx.as_table_alias != null) {
        val tableName = getCleanId(ctx.as_table_alias.table_alias.id)

        schema.alias = tableName +: schema.alias
        schema.log(s"+ Table alias ${schema.alias.head} -> ${schema.alias.tail.mkString(",")}")
      }

      //      val ruleA = ctx.getRuleIndex
      //      val aName = schema.parser.getRuleNames()(ruleA)
      //      var typ = ctx.LOCAL_ID()
      //      println(s">>>> type ? ${ctx.children.asScala.head.getClass} $tableName $aName")
      //      println(s">>>> type ? $aName $typ")

      if (ctx.LOCAL_ID() != null) {
        schema.log("LocalID")
        val tableName = ctx.LOCAL_ID().getText
        schema.log(s"\t+ add local table $tableName")
        schema.addTable(tableName) // , derived = true
        if (schema.alias.headOption.nonEmpty) {
          schema.log(s"\t> add table scope $tableName (${schema.alias.head})")
          schema.addTableScope(schema.alias.head, tableName)
        }
      }

      visitChildren(ctx)

      if (ctx.as_table_alias != null) {
        schema.log(s"- Table alias ${schema.alias.head} <- ${schema.alias.tail.mkString(",")}")
        schema.alias = schema.alias.tail
      }
    }
    schema
  }

  override def visitFull_table_name(ctx: TSqlParser.Full_table_nameContext): Schema = {
    schema.log("FullTableName")
    val tableName = getCleanId(ctx.table)

    //    val derived: Boolean = (schema.derivedTable.headOption.getOrElse("") == tableName)
    if (tableName == schema.derivedTable.headOption.getOrElse("")) {
      schema.log(s"\t? table $tableName derived")
      return schema
    }

    schema.log(s"\t+ table $tableName (${schema.alias.headOption.getOrElse("")})")
    var table = schema.addTable(tableName)
    if (schema.alias.nonEmpty) {
      schema.log(s"\t> add table scope $tableName (${schema.alias.headOption.getOrElse("")})")
      schema.addTableScope(schema.alias.head, tableName)
    }
    else if (schema.fromTableScope(tableName) isDefined) {
      table = schema.addTable(schema.fromTableScope(tableName).get)
    }
    addTableTrace(table, ctx)
    visitChildren(ctx)
    schema
  }

  //  override def visitConstant_LOCAL_ID (ctx: TSqlParser.Constant_LOCAL_IDContext): Schema = {
  //    schema.log("LocalID")
  //    val tableName = ctx.LOCAL_ID().getText
  //    schema.log(s"\t+ add local table $tableName")
  //    schema.addTable(tableName, derived = true)
  ////    return visitChildren(ctx)
  //    schema
  //  }

  override def visitTable_name(ctx: TSqlParser.Table_nameContext): Schema = {
    schema.log("TableName")
    val tableName = getCleanId(ctx.table)
    schema.log(s"No hint table == $tableName")
    noHintTable = Some(tableName)

    if (tableName == schema.derivedTable.headOption.getOrElse("")) {
      schema.log(s"\t? table $tableName derived")
      return schema
    }

    var table: Table = null
    if (schema.alias.nonEmpty) {
      schema.log(s"\t> add table scope $tableName (${schema.alias.headOption.getOrElse("")})")
      schema.addTableScope(schema.alias.head, tableName)
    }
    else {
      if (schema.fromTableScope(tableName) isDefined) {
        table = schema.addTable(schema.fromTableScope(tableName).get)
      }
      else {
        table = schema.addTable(tableName)
      }
      schema.log(s"\t+ table $tableName")

      addTableTrace(table, ctx)
    }
    visitChildren(ctx)
    schema
  }

  override def visitFull_column_name(ctx: TSqlParser.Full_column_nameContext): Schema = {
    schema.log("FullColumnElem")

    val tableName: String = if (ctx.table_name != null) getCleanId(ctx.table_name.table) else ""
    val columnName: String = getCleanId(ctx.column_name)
    val columnAlias: String = ""

    //    if (tableName.nonEmpty) {
    //      if (tableName == schema.derivedTable.headOption.getOrElse("")) {
    //        schema.log(s"\t? table $tableName derived, skip")
    //        return schema
    //      }
    //
    //      if (schema.fromTableScope(tableName).isDefined) {
    //        table = schema.addTable(schema.fromTableScope(tableName).get)
    //        schema.log(s"\ttable rename ${table.name} (from existing tables)")
    //      }
    //      else {
    //        table = schema.addTable(tableName)
    //      }
    //
    //      schema.log(s"\t+ column $columnName in ${table.name}")
    //
    //      val column = table.addColumn(columnName)
    //
    //      addTableTrace(table, ctx)
    //      addColumnTrace(column, ctx)
    //      //    visitChildren(ctx)
    //    } else {
    //      // todo use DDL
    //      schema.log(s"\t? column $columnName skipped, no table found")
    //    }
    //    schema

    addColumn(ctx, tableName, columnName, columnAlias)
  }

  def getCleanId(id: IdContext): String = {
    //    if(id.getText.startsWith("[")){
    //      val a=1
    //    }

    //    schema.log(s"\tCleaning id: ${id.getText}")
    if (id.simple_id() != null) {
      return id.getText
    }
    if (id.DOUBLE_QUOTE_ID() != null) {
      return id.getText.stripMargin('"')
    }
    if (id.SQUARE_BRACKET_ID() != null) {
      return id.getText.stripPrefix("[").stripSuffix("]")
    }
    id.getText
  }

  def getCleanId(id: String): String = {
    //    if(id.getText.startsWith("[")){
    //      val a=1
    //    }

    //    schema.log(s"\tCleaning string: $id")
    id.stripMargin('\'')
  }

  override def visitColumn_elem(ctx: TSqlParser.Column_elemContext): Schema = {
    schema.log("ColumnElem")

    val tableName: String = if (ctx.table_name() != null) getCleanId(ctx.table_name().table) else ""
    val columnName: String = getCleanId(ctx.column_name)
    val columnAlias: String = if (ctx.as_column_alias() != null) {
      if (ctx.as_column_alias().column_alias().id() != null) getCleanId(ctx.as_column_alias().column_alias().id()) else getCleanId(ctx.as_column_alias().column_alias().STRING().getText)
    } else ""

    addColumn(ctx, tableName, columnName, columnAlias)
  }

  def addColumn(ctx: ParserRuleContext, _tableName: String, _columnName: String, _columnAlias: String): Schema = {
    var table: Table = null
    var column: Column = null
    var tableName: String = _tableName
    var columnName: String = _columnName
    val columnAlias: String = _columnAlias

    schema.log(s"\tlooking for $tableName.$columnName, table hint ($noHintTable)")

    if (tableName.nonEmpty) {
      val maybeColumnAlias = schema.fromColumnScope(columnName, tableName)
      if (maybeColumnAlias.isDefined && maybeColumnAlias.get.level > 0) {

        table = schema.tableByName(maybeColumnAlias.get.table).get
        tableName = table.name
        columnName = maybeColumnAlias.get.column

        schema.log(s"\tfound column alias => $tableName.$columnName")
      }

      if (schema.fromTableScope(tableName).isDefined) {
        table = schema.addTable(schema.fromTableScope(tableName).get)
      }
      else {
        table = schema.addTable(tableName)
      }

      if (columnAlias.nonEmpty) {
        schema.log(s"\t> add column scope ${table.name}.$columnName as $columnAlias")
        schema.addColumnScope(columnAlias, table.name, columnName)
      }
      else {
        schema.log(s"\t> add column scope ${table.name}.$columnName")
        schema.addColumnScope(columnName, table.name, columnName)
      }

      schema.log(s"\t+ column $columnName ($columnAlias) with table hint ${table.name}")
    }

    // todo scope
    else if (schema.tables.count(!_.derived) >= 1 && noHintTable.nonEmpty) {
      // table = schema.tables.filter(!_.derived).head
//      val dt = schema.tables.filter(d => !d.derived)
//      table = schema.tableScopes.head.filter(t => dt.contains(t._1)).head
      val maybeTable = schema.tables.find(_.name == noHintTable.get)
      if(maybeTable.isEmpty){
        schema.log(s"\t> no hint table ${noHintTable.get} not found")
        return schema
      }
      table = maybeTable.get

      if (columnAlias.nonEmpty) {
        schema.log(s"\t> add column scope ${table.name}.$columnName as $columnAlias")
        schema.addColumnScope(columnAlias, table.name, columnName)
      }
      else {
        schema.log(s"\t> add column scope ${table.name}.$columnName")
        schema.addColumnScope(columnName, table.name, columnName)
      }

      schema.log(s"\t+ column $columnName without table hint ${table.name}")

      //      column = table.addColumn(ctx.column_name.getText)

      addTableTrace(table, ctx)
      //      addColumnTrace(column, ctx)
    }
    else {
      // todo
      schema.log(s"\ttable not found")
      return schema
    }
    column = table.addColumn(columnName)
    addColumnTrace(column, ctx)
    visitChildren(ctx)
    schema
  }

  def resolveTable(aI: Full_column_nameContext, i: Int): (String, String) = {
    var tableName: String = null
    var tableNameFirst: String = null
    var fieldName: String = null

    if (aI.table_name() != null) {
      tableName = getCleanId(aI.table_name().table)
      tableNameFirst = tableName

      schema.log(s"table $i name $tableName")
      if (schema.fromTableScope(tableName) isDefined) {
        val tableRename = schema.addTable(schema.fromTableScope(tableName).get)
        schema.log(s"table $i rename $tableName => ${tableRename.name} (found in table scope)")
        tableName = tableRename.name
      }
    }
    if (aI.id() != null) {
      fieldName = getCleanId(aI.id())
    }
    //    if (tableNameFirst == null) {
    //    if (schema.fromTableScope(tableNameFirst).isEmpty && schema.fromColumnScope(fieldName).isDefined) {
    val maybeColumnAlias = schema.fromColumnScope(fieldName, tableName)
    //    if (maybeColumnAlias.isDefined && !schema.derivedTable.contains(maybeColumnAlias.get.table)) {
    if (maybeColumnAlias.isDefined && maybeColumnAlias.get.level > 0) {
      schema.log(s"table $i rename $tableName => ${maybeColumnAlias.get.table} (found in column scope with ${maybeColumnAlias.get.column}, level ${maybeColumnAlias.get.level})")
      tableName = maybeColumnAlias.get.table
      fieldName = maybeColumnAlias.get.column
    }
    //    else if (schema.fromTableScope(tableName) isDefined) {
    //      tableName = schema.fromTableScope(tableName).get
    //      schema.log(s"table $i rename $tableName")
    //    }

    if (tableName != null) {
      schema.log(s"\t+ when resolve, declare $tableName.$fieldName")
      val table = schema.addTable(tableName)
      val column = table.addColumn(fieldName)
    }
    // todo add trace
    //    addColumnTrace(column, aI)

    (tableName, fieldName)
  }

  override def visitPredicate(ctx: TSqlParser.PredicateContext): Schema = {
    visitChildren(ctx)
    val ctxOperator = ctx.comparison_operator()
    if (ctxOperator != null) {
      // todo other operators
      if (ctxOperator.start.getType == TSqlLexer.EQUAL && ctx.expression().asScala.length == 2) {
        schema.log(s"Condition = at ${ctxOperator.start.getLine}:${ctxOperator.start.getCharPositionInLine}")
        val a = ctx.expression(0)
        val b = ctx.expression(1)
        //val ruleA = a.getRuleIndex
        //val aName = schema.parser.getRuleNames()(ruleA)
        //        schema.println(s"\t+ relation ? ${a.children.asScala.head.getClass}")
        var table1: String = null
        var field1: String = null
        var table2: String = null
        var field2: String = null
        a.children.asScala.head match {
          case aI: Full_column_nameContext => val (tableName, fieldName) = resolveTable(aI, 1); table1 = tableName; field1 = fieldName
          case _ =>
        }
        b.children.asScala.head match {
          case aI: Full_column_nameContext => val (tableName, fieldName) = resolveTable(aI, 2); table2 = tableName; field2 = fieldName
          case _ =>
        }


        if (table1 != null && field1 != null && table2 != null && field2 != null && table1.nonEmpty && field1.nonEmpty && table2.nonEmpty && field2.nonEmpty) {
          //          if (schema.fromColumnScope(field1) isDefined){
          //            table1 = schema.fromColumnScope(field1).get._1
          //            schema.log(s"table rename $table1")
          //            field1 = schema.fromColumnScope(field1).get._2
          //          } else if (schema.fromTableScope(table1) isDefined) {
          //            table1 = schema.fromTableScope(table1).get
          //          }
          //
          //          if (schema.fromColumnScope(field2) isDefined){
          //            table2 = schema.fromColumnScope(field2).get._1
          //            field2 = schema.fromColumnScope(field2).get._2
          //          } else if (schema.fromTableScope(table2) isDefined) {
          //            table2 = schema.fromTableScope(table2).get
          //          }

          if (table1 == table2 && field1 == field2) {
            return schema
          }

          if (schema.relations.exists(r =>
            r.table1 == table1 && r.table2 == table2 && r.field1 == field1 && r.field2 == field2 ||
              r.table1 == table2 && r.table2 == table1 && r.field1 == field2 && r.field2 == field1
          )) {
            return schema
          }

          schema.log(s"\t+ relation between $table1, $field1, $table2, $field2")
          schema.addRelation(table1, field1, table2, field2)
        }
      }
    }
    else if (ctx.IN() != null && ctx.subquery() != null) {
      schema.log(s"Condition IN")
      val a = ctx.expression(0)
      val b = ctx.subquery().select_statement().query_expression().query_specification().select_list().select_list_elem(0)

      if (b == null) {
        // todo
        schema.log(s"\t? no select found")
        return schema
      }
      //val ruleA = a.getRuleIndex
      //val aName = schema.parser.getRuleNames()(ruleA)
      //        schema.println(s"\t+ relation ? ${a.children.asScala.head.getClass}")
      var table1: String = null
      var field1: String = null
      var table2: String = null
      var field2: String = null
      // todo parse b before
      a.children.asScala.head match {
        case aI: Full_column_nameContext =>
          if (aI.table_name() != null) {
            // todo refactor
            table1 = aI.table_name().getText

            schema.log(s"table 1 name $table1")
            if (schema.fromTableScope(table1) isDefined) {
              table1 = schema.addTable(schema.fromTableScope(table1).get).name
              schema.log(s"table 1 rename $table1")
            }
          }
          if (aI.id() != null) {
            field1 = aI.id().getText
          }
        case _ =>
      }
      // todo use visitor for select, get back first table.column
      if (b.column_elem() != null) {
        val aI = b.column_elem()
        if (aI.table_name() != null) {
          schema.log(s"table 2 name $table2")
          table2 = aI.table_name().getText
          if (schema.fromTableScope(table2) isDefined) {
            schema.log(s"table 2 rename $table2")
            table2 = schema.addTable(schema.fromTableScope(table2).get).name
          }
        }
        if (aI.id() != null) {
          field2 = aI.id().getText
        }
      }

      if (table1 == null) {
        if (schema.fromColumnScope(field1) isDefined) {
          table1 = schema.fromColumnScope(field1).get.table
          schema.log(s"table 1 rename $table1")
          field1 = schema.fromColumnScope(field1).get.column
        } else if (schema.fromTableScope(table1) isDefined) {
          table1 = schema.fromTableScope(table1).get
        }
      }

      if (table2 == null) {
        if (schema.fromColumnScope(field2) isDefined) {
          table2 = schema.fromColumnScope(field2).get.table
          schema.log(s"table 2 rename $table2")
          field2 = schema.fromColumnScope(field2).get.column
        } else if (schema.fromTableScope(table2) isDefined) {
          table2 = schema.fromTableScope(table2).get
        }
      }

      if (table1 != null && field1 != null && table2 != null && field2 != null) {
        //          if (schema.fromColumnScope(field1) isDefined){
        //            table1 = schema.fromColumnScope(field1).get._1
        //            schema.log(s"table rename $table1")
        //            field1 = schema.fromColumnScope(field1).get._2
        //          } else if (schema.fromTableScope(table1) isDefined) {
        //            table1 = schema.fromTableScope(table1).get
        //          }
        //
        //          if (schema.fromColumnScope(field2) isDefined){
        //            table2 = schema.fromColumnScope(field2).get._1
        //            field2 = schema.fromColumnScope(field2).get._2
        //          } else if (schema.fromTableScope(table2) isDefined) {
        //            table2 = schema.fromTableScope(table2).get
        //          }

        if (table1 == table2 && field1 == field2) {
          return schema
        }

        if (schema.relations.exists(r =>
          r.table1 == table1 && r.table2 == table2 && r.field1 == field1 && r.field2 == field2 ||
            r.table1 == table2 && r.table2 == table1 && r.field1 == field2 && r.field2 == field1
        )) {
          return schema
        }

        schema.log(s"\t+ relation between $table1, $field1, $table2, $field2")
        schema.addRelation(table1, field1, table2, field2)
      }
    }
    schema
  }
}
