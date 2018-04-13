package grammars

import grammars.tsql.TSqlParser
import play.api.libs.json._

import scala.collection._

class Schema(var fileId: String) {
  var logEnabled = true

  def getSortedRelations: Seq[Relation] = {
    relations.sortBy(r => (r.table1, r.table2, r.field1, r.field2))
  }

  def getFlippedRelations: Seq[Relation] = {
    relations.map(r => if (r.table1 + "." + r.field1 < r.table2 + "." + r.field2) r else new Relation(r.table2, r.field2, r.table1, r.field1))
  }

  var parser: TSqlParser = _
  var tables: Seq[Table] = Seq[Table]()
  var relations: Seq[Relation] = Seq[Relation]()
  var tableScopes: List[TableScope] = List(new TableScope(null))
  var columnScopes: List[ColumnScope] = List(new ColumnScope(null))

  var alias: Seq[String] = Seq[String]()
  var derivedTable: Seq[String] = Seq[String]()

  def closeScope(): Schema = {
    log(s"* close scope level ${tableScopes.length}")
    val _ :: tableScopesTail = tableScopes
    tableScopes = tableScopesTail
    val _ :: columnScopesTail = columnScopes
    columnScopes = columnScopesTail
    this
  }

  def openScope(): Schema = {
    log(s"* open scope level ${tableScopes.length + 1}")
    val tablesScopesHead :: _ = tableScopes
    tableScopes = new TableScope(tablesScopesHead) :: tableScopes
    val columnsScopeHead :: _ = columnScopes
    columnScopes = new ColumnScope(columnsScopeHead) :: columnScopes
    this
  }

  def addTableScope(alias: String, table: String): Unit = {
    tableScopes.head += alias -> table
  }

  def addColumnScope(columnAlias: String, tableName: String, columnName: String, siblingsOnly: Boolean = false): Unit = {

    val level = if (siblingsOnly) columnScopes.length else 0

    val existingScope = fromColumnScope(columnAlias).getOrElse(new ColumnAlias("", "", "", 0))
    val existingTableName = existingScope.table
    val existingColumnName = existingScope.column

    log(s"* add column scope $columnAlias -> $tableName.$columnName, level $level, existing $existingTableName.$existingColumnName")
    columnScopes.head += columnAlias -> new ColumnAlias(columnAlias, tableName, columnName, level)
  }

  def fromTableScope(alias: String): Option[String] = {
    tableScopes.head.first(alias)
  }

  def fromColumnScope(alias: String, tableAlias: String = null): Option[ColumnAlias] = {
    val a: Option[ColumnAlias] = if (tableAlias == null) columnScopes.head.first(alias) else columnScopes.head.first(tableAlias, alias)
    if (a.nonEmpty && (a.get.level == 0 || a.get.level >= columnScopes.length)) {
      return a
    }
    None
    //    a
  }

  def addTable(name: String, derived: Boolean = false): Table = {
    val existingTable = tableByName(name)
    if (existingTable isDefined) {
      return existingTable.get
    }

    val table = new Table(name, derived)
    tables = tables :+ table
    table
  }

  def addRelation(table1: String, field1: String, table2: String, field2: String): Relation = {
    // todo unique relation
    //    val existingTable = tableByName(table1)
    //    if (existingTable isDefined) {
    //      return existingTable.get
    //    }

    val relation = new Relation(table1, field1, table2, field2)
    relations = relations :+ relation
    relation
  }

  def tableByName(name: String): Option[Table] = {
    if (tables isEmpty) return Option.empty[Table]
    tables.find(_.name == name)
  }

  def log(str: String): Unit = {
    if (logEnabled) {
      println(("\t" * (tableScopes.length - 1)) + str)
    }
  }

  def marshallJson(): String = {
    implicit class RichJsObject(original: JsObject) {
      def omitEmpty: JsObject = original.value.foldLeft(original) {
        case (obj, (key, JsString(st))) if st == null || st.isEmpty => obj - key
        case (obj, (key, JsArray(arr))) if arr == null || arr.isEmpty => obj - key
        case (obj, (_, _)) => obj
      }
    }

    implicit val columnWrites = new Writes[Column] {
      def writes(column: Column): JsObject = {
        val traces: Seq[String] = if (column.traces != null) column.traces.map(t => s"${t.file}:${t.line}:${t.column}") else Seq[String]()
        Json.obj(
          "name" -> column.name,
          "traces" -> traces
        ).omitEmpty
      }
    }

    implicit val tableWrites = new Writes[Table] {
      def writes(table: Table): JsObject = {
        val traces: Seq[String] = if (table.traces != null) table.traces.map(t => s"${t.file}:${t.line}:${t.column}") else Seq[String]()
        Json.obj(
          "name" -> table.name,
          "traces" -> traces,
          "columns" -> table.columns
        ).omitEmpty
      }
    }

    implicit val schemaWrites = new Writes[Schema] {
      def writes(schema: Schema): JsObject = Json.obj(
        "tables" -> schema.tables,
        "relations" -> schema.relations.map(r => Seq[String](
          s"${r.table1}.${r.field1}",
          s"${r.table2}.${r.field2}"
        ))
      ).omitEmpty
    }

    val json = Json.prettyPrint(Json.toJson(this))
    json
  }

  def marshallGraphNodeFields(c: Column): String = {
    s"<${c.name}> ${c.name}"
  }

  def marshallGraphNode(t: Table): String = {
    val graphNodeFields: String = t.columns.map(marshallGraphNodeFields).mkString("|")

    s"""
       |  "${t.name}" [
       |    label = "${t.name}|$graphNodeFields"
       |    shape = "record"
       |  ];""".stripMargin
  }

  def marshallGraphRelation(r: Relation, id: Int): String = {
    s"""
       |  "${r.table1}":${r.field1} -- "${r.table2}":${r.field2} [
       |    dir = none,
       |    id = $id,
       |    colorscheme = dark28,
       |    color = black,
       |    penwidth = 3
       |  ];""".stripMargin
  }

  def marshallGraph(): String = {
    val graphNodes: String = tables.map(marshallGraphNode).mkString("")
    val graphRelations: String = relations.zipWithIndex.map(z => marshallGraphRelation(z._1, z._2)).mkString("")
    val graph: String =
      s"""
         |graph g {
         |  graph [
         |    rankdir = "LR",
         |    ranksep = "1.0"
         |  ];
         |  node [
         |   fontsize = "10"
         |   shape = "record",
         |   fontname = "Calibri"
         |  ];
         |  edge [
         |  ];
         |  $graphNodes
         |  $graphRelations
         |}
      """.stripMargin.trim
    graph
  }

  def merge(other: Schema): Unit = {
    val tablesPart = other.tables.partition(o => tables.exists(t => t.name == o.name))
    tables = tables ++ tablesPart._2
    tablesPart._1.foreach(o => {
      val table = tables.find(_.name == o.name).get
      val columnsPart = o.columns.partition(oc => table.columns.exists(tc => tc.name == oc.name))
      table.columns = table.columns ++ columnsPart._2
    })

    relations = relations ++ other.getFlippedRelations
      .map(r => (r.table1, r.field1, r.table2, r.field2))
      .diff(relations.map(r => (r.table1, r.field1, r.table2, r.field2)))
      .map(r => new Relation(r._1, r._2, r._3, r._4))
  }
}

class ColumnAlias(val alias: String, val table: String, val column: String, val level: Int = 0) {}

class TableScope(val parent: TableScope) extends scala.collection.mutable.HashMap[String, String] {
  def inScope(varName: String): Boolean = {
    if (super.contains(varName)) return true
    if (parent == null) false
    else parent.inScope(varName)
  }

  def first(varName: String): Option[String] = {
    if (super.contains(varName)) return super.get(varName)
    if (parent == null) Option.empty[String]
    else parent.first(varName)
  }
}

class ColumnScope(val parent: ColumnScope) extends scala.collection.mutable.HashMap[String, ColumnAlias] {
  def inScope(varName: String): Boolean = {
    if (super.contains(varName)) return true
    if (parent == null) false
    else parent.inScope(varName)
  }

  def first(varName: String): Option[ColumnAlias] = {
    if (super.contains(varName)) return super.get(varName)
    if (parent == null) Option.empty[ColumnAlias]
    else parent.first(varName)
  }

  def first(tableName: String, varName: String): Option[ColumnAlias] = {
    if (super.contains(tableName + "." + varName)) return super.get(tableName + "." + varName)
    if (parent == null) Option.empty[ColumnAlias]
    else parent.first(tableName, varName)
  }
}

abstract class Identifier(name: String) {
  var aliases: Seq[String]
  var traces: Seq[Trace]

  def addTrace(trace: Trace) {
    if (traces == null) {
      traces = Seq[Trace](trace)
    } else {
      traces = traces :+ trace
    }
  }
}

class Table(var name: String, var derived: Boolean) extends Identifier(name) {
  var columns: Seq[Column] = Seq[Column]()

  override var aliases: Seq[String] = _
  override var traces: Seq[Trace] = _

  def addColumn(name: String): Column = {
    val existingColumn = columnByName(name)
    if (existingColumn isDefined) {
      return existingColumn.get
    }

    val column = new Column(name)
    columns = columns :+ column
    column
  }

  def columnByName(name: String): Option[Column] = {
    if (columns isEmpty) return Option.empty[Column]
    columns.find(_.name == name)
  }
}

class Column(var name: String) extends Identifier(name) {
  override var aliases: Seq[String] = _
  override var traces: Seq[Trace] = _
}

class Trace(var file: String, var line: Int, var column: Int) {
}

class Relation(var table1: String, var field1: String, var table2: String, var field2: String) {
}

class Field(var table: String, var column: String) {}
