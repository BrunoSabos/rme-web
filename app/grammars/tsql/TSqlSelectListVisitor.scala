package grammars.tsql

import grammars.tsql.TSqlParser.{Column_elemContext, Full_column_nameContext}
import grammars.{Column, Schema, Table, Trace}
import org.antlr.v4.runtime.ParserRuleContext

import scala.collection.JavaConverters._
import scala.collection._

class TSqlSelectListVisitor(val schema: Schema) extends TSqlParserBaseVisitor[Schema] {

  def addTableTrace(table: Table, ctx: ParserRuleContext): Unit = {
    schema.log(s"\t\t+ trace for table ${table.name}, ${ctx.start.getLine}:${ctx.start.getCharPositionInLine}")
    table.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
  }

  def addColumnTrace(column: Column, ctx: ParserRuleContext): Unit = {
    schema.log(s"\t\t+ trace for column ${column.name}, ${ctx.start.getLine}:${ctx.start.getCharPositionInLine}")
    column.addTrace(new Trace(schema.fileId, ctx.start.getLine, ctx.start.getCharPositionInLine))
  }

  override def visitTable_source_item (ctx: TSqlParser.Table_source_itemContext): Schema =  {

    if(ctx.derived_table() != null){
      val tableName = ctx.as_table_alias.table_alias.id.getText

      schema.derivedTable = tableName +: schema.derivedTable
      schema.log(s"+ Derived table ${schema.derivedTable.head} -> ${schema.derivedTable.tail.mkString(",")}")
      schema.addTable(tableName, derived = true)
      schema.log(s"> Push scope")
      schema.openScope()

      val subQueryVisitor = new TSqlQuerySpecificationVisitor(schema)
      subQueryVisitor.visit(ctx)
      var exT = schema.columnScopes.head

      schema.log(s"< Pop scope")
      schema.closeScope()

      schema.log(s"\tcolumns aliases extraction: " + exT.map(e => s"${e._1} -> ${schema.derivedTable.head} (${e._2._1}).${e._2._2}").mkString(", "))
//      schema.log(s"\tderived columns extraction: " + exT.map(e => s"${e._1} -> ${e._2._1}.${e._2._2}").mkString(","))

      exT.foreach(e => schema.addColumnScope(e._1, e._2._1, e._2._2))

//      schema.log(s"\ttable derived columns extraction: " + exT.map(e => s"${e._1} -> ${schema.derivedTable.head} (${e._2._1}).${e._2._2}").mkString(", "))
      //      schema.log(s"\tderived columns extraction: " + exT.map(e => s"${e._1} -> ${e._2._1}.${e._2._2}").mkString(","))

//      exT.foreach(e => schema.addColumnScope(e._1, e._2._1, e._2._2))

      schema.log(s"- Derived table ${schema.derivedTable.head} <- ${schema.derivedTable.tail.mkString(",")}")
      schema.derivedTable = schema.derivedTable.tail
    }
    else {
      if(ctx.as_table_alias != null) {
        val tableName = ctx.as_table_alias.table_alias.id.getText

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
        if(schema.alias.headOption.nonEmpty){
          schema.log(s"\t> add table scope $tableName (${schema.alias.head})")
          schema.addTableScope(schema.alias.head, tableName)
        }
      }

      visitChildren(ctx)

      if(ctx.as_table_alias != null) {
        schema.log(s"- Table alias ${schema.alias.head} <- ${schema.alias.tail.mkString(",")}")
        schema.alias = schema.alias.tail
      }
    }
    schema
  }

  override def visitFull_table_name (ctx: TSqlParser.Full_table_nameContext): Schema = {
    schema.log("FullTableName")
    val tableName = ctx.table.getText

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
    else if(schema.fromTableScope(tableName) isDefined)
    {
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

  override def visitTable_name (ctx: TSqlParser.Table_nameContext): Schema = {
    schema.log("TableName")
    val tableName = ctx.table.getText

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

  override def visitFull_column_name (ctx: TSqlParser.Full_column_nameContext): Schema = {
    schema.log("FullColumnElem")
    var table: Table = null

    var alias: String = ""
    var aliasColumn: String = ""
    if (ctx.table_name != null) {
      alias = ctx.table_name.getText

      if (alias == schema.derivedTable.headOption.getOrElse("")) {
        schema.log(s"\t? table $alias derived")
        return schema
      }

      if (schema.fromTableScope(alias).isDefined) {
        table = schema.addTable(schema.fromTableScope(alias).get)
      }
      else {
        table = schema.addTable(alias)
      }

      schema.log(s"\t+ column ${ctx.column_name.getText} in ${table.name}")

      val column = table.addColumn(ctx.column_name.getText)

      addTableTrace(table, ctx)
      addColumnTrace(column, ctx)
      //    visitChildren(ctx)
    } else {
      schema.log(s"\t? column $aliasColumn, no table found")
    }
    schema
  }

  override def visitColumn_elem(ctx: TSqlParser.Column_elemContext): Schema = {
    schema.log("ColumnElem")

    var table: Table = null
    var column: Column = null
    val tableName = ctx.table_name()
    var aliasColumn: String = ""
    aliasColumn = ctx.column_name.getText
    var aliasColumn1: String = ""

    if(tableName != null) {
      var tableAlias: String = tableName.getText




      var column: Column = null
      if (schema.fromColumnScope(aliasColumn).isDefined) {
//        table = schema.addTable(schema.fromColumnScope(aliasColumn).get._1)
//        column = table.addColumn(schema.fromColumnScope(aliasColumn).get._2)

        table = schema.tableByName(schema.fromColumnScope(aliasColumn).get._1).get
        tableAlias = table.name
        aliasColumn = schema.fromColumnScope(aliasColumn).get._2
      }



      if (schema.fromTableScope(tableAlias).isDefined) {
        table = schema.addTable(schema.fromTableScope(tableAlias).get)
      }
      else
      {
        table = schema.addTable(tableAlias)
      }

//      schema.log(s"? table ${schema.derivedTable.mkString(",")} derived")
      if (table.name == schema.derivedTable.headOption.getOrElse("")) {
        schema.log(s"? table ${table.name} derived")
      }

      aliasColumn1 = aliasColumn
      if (ctx.as_column_alias() != null) {
        aliasColumn1 = ctx.as_column_alias().column_alias().getText

        schema.log(s"\t> add column scope ${table.name}.$aliasColumn as $aliasColumn1")
        schema.addColumnScope(aliasColumn1, table.name, aliasColumn)
      }

      schema.log(s"\t> add column scope ${table.name}.$aliasColumn")
      schema.addColumnScope(aliasColumn, table.name, aliasColumn)

//      column = table.addColumn(ctx.column_name.getText)

//        addTableTrace(table, ctx)
//        addColumnTrace(column, ctx)
//      return schema
      schema.log(s"\t+ column $aliasColumn ($aliasColumn1) with table hint ${table.name}")
    }
    // todo scope
    else if(schema.tables.count(!_.derived) == 1)
    {
      table = schema.tables.filter(!_.derived).head

      aliasColumn1 = aliasColumn
      if (ctx.as_column_alias() != null) {
        aliasColumn1 = ctx.as_column_alias().column_alias().getText

        schema.log(s"\t> add column scope ${table.name}.$aliasColumn as $aliasColumn1")
        schema.addColumnScope(aliasColumn1, table.name, aliasColumn)
      }

      schema.log(s"\t> add column scope ${table.name}.$aliasColumn")
      schema.addColumnScope(aliasColumn, table.name, aliasColumn)

      schema.log(s"\t+ column $aliasColumn without table hint ${table.name}")

//      column = table.addColumn(ctx.column_name.getText)

      addTableTrace(table, ctx)
//      addColumnTrace(column, ctx)
    }
    else {
      // todo
      return schema
    }
    column = table.addColumn(aliasColumn)
//    addTableTrace(table, ctx)
    addColumnTrace(column, ctx)
    visitChildren(ctx)
    schema
  }

  override def visitPredicate (ctx: TSqlParser.PredicateContext): Schema = {
    visitChildren(ctx)
    if(ctx.comparison_operator() != null) {
      // todo other operators
      if(ctx.comparison_operator().start.getType == TSqlLexer.EQUAL && ctx.expression().asScala.length == 2) {
        schema.log(s"Condition =")
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
          case aI: Full_column_nameContext =>
            if (aI.table_name() != null) {
              // todo refact
              table1 = aI.table_name().getText

              schema.log(s"table 1 name $table1")
              if(schema.fromTableScope(table1) isDefined) {
                table1 = schema.addTable(schema.fromTableScope(table1).get).name
                schema.log(s"table 1 rename $table1")
              }
            }
            if (aI.id() != null) {
              field1 = aI.id().getText
            }
          case _ =>
        }
        b.children.asScala.head match {
          case aI: Full_column_nameContext =>
            if (aI.table_name() != null) {
              table2 = aI.table_name().getText
              schema.log(s"table 2 name $table2")
              if(schema.fromTableScope(table2) isDefined) {
                table2 = schema.addTable(schema.fromTableScope(table2).get).name
                schema.log(s"table 2 rename $table2")
              }
            }
            if (aI.id() != null) {
              field2 = aI.id().getText
            }
          case _ =>
        }

//        if(table1 == null) {
            if (
//              schema.derivedTable.contains(table1) &&
                schema.fromColumnScope(field1).isDefined){
              table1 = schema.fromColumnScope(field1).get._1
              schema.log(s"table 1 rename $table1")
              field1 = schema.fromColumnScope(field1).get._2
            } else if (schema.fromTableScope(table1) isDefined) {
              table1 = schema.fromTableScope(table1).get
              schema.log(s"table 1 rename $table1")
            }
//        }

//        if(table2 == null){
            if (
//              schema.derivedTable.contains(table2) &&
                schema.fromColumnScope(field2).isDefined){
              table2 = schema.fromColumnScope(field2).get._1
              schema.log(s"table 2 rename $table2")
              field2 = schema.fromColumnScope(field2).get._2
            } else if (schema.fromTableScope(table2) isDefined) {
              table2 = schema.fromTableScope(table2).get
              schema.log(s"table 2 rename $table2")
            }
//        }

        if(table1 != null && field1 != null && table2 != null && field2 != null) {
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
    else if(ctx.IN() != null && ctx.subquery() != null) {
      schema.log(s"Condition IN")
      val a = ctx.expression(0)
      val b = ctx.subquery().select_statement().query_expression().query_specification().select_list().select_list_elem(0)

      if (b==null){
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
      a.children.asScala.head match {
        case aI: Full_column_nameContext =>
          if (aI.table_name() != null) {
            // todo refact
            table1 = aI.table_name().getText

            schema.log(s"table 1 name $table1")
            if(schema.fromTableScope(table1) isDefined) {
              table1 = schema.addTable(schema.fromTableScope(table1).get).name
              schema.log(s"table 1 rename $table1")
            }
          }
          if (aI.id() != null) {
            field1 = aI.id().getText
          }
        case _ =>
      }
      if (b.column_elem() != null){
        var aI = b.column_elem()
        if (aI.table_name() != null) {
          schema.log(s"table 2 name $table2")
          table2 = aI.table_name().getText
          if(schema.fromTableScope(table2) isDefined) {
            schema.log(s"table 2 rename $table2")
            table2 = schema.addTable(schema.fromTableScope(table2).get).name
          }
        }
        if (aI.id() != null) {
          field2 = aI.id().getText
        }
      }

      if(table1 == null) {
        if (schema.fromColumnScope(field1) isDefined){
          table1 = schema.fromColumnScope(field1).get._1
          schema.log(s"table 1 rename $table1")
          field1 = schema.fromColumnScope(field1).get._2
        } else if (schema.fromTableScope(table1) isDefined) {
          table1 = schema.fromTableScope(table1).get
        }
      }

      if(table2 == null){
        if (schema.fromColumnScope(field2) isDefined){
          table2 = schema.fromColumnScope(field2).get._1
          schema.log(s"table 2 rename $table2")
          field2 = schema.fromColumnScope(field2).get._2
        } else if (schema.fromTableScope(table2) isDefined) {
          table2 = schema.fromTableScope(table2).get
        }
      }

      if(table1 != null && field1 != null && table2 != null && field2 != null) {
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
