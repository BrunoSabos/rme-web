package grammars

import grammars.tsql.TSqlParser
import play.api.libs.json._

import scala.collection._
import scala.io._

class Schema (var fileId: String){
  var parser: TSqlParser = null
  var tables: Seq[Table] = Seq[Table]()
  var relations: Seq[Relation] = Seq[Relation]()
  var scopes: List[Scope] = List(new Scope(null))

  def closeScope(): Schema = {
    val _ :: s = scopes
    scopes = s
    this
  }

  def openScope(): Schema = {
    val parent :: _ = scopes
    scopes = new Scope(parent) :: scopes
    this
  }

  def addScope(alias: String, table: String): Unit = {
    scopes.head += alias -> table
  }

  def fromScope(alias: String): Option[String] = {
    scopes.head.first(alias)
  }

  def addTable(name: String): Table = {
    val existingTable = tableByName(name)
    if (existingTable isDefined) {
      return existingTable.get
    }

    val table = new Table(name)
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
    if(tables isEmpty) return Option.empty[Table]
    tables.find(_.name == name)
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
      def writes(column: Column) = Json.obj(
        "name" -> column.name,
        "traces" -> column.traces.map(t => s"${t.file}:${t.line}:${t.column}")
      ).omitEmpty
    }

    implicit val tableWrites = new Writes[Table] {
      def writes(table: Table) = Json.obj(
        "name" -> table.name,
        "traces" -> table.traces.map(t => s"${t.file}:${t.line}:${t.column}"),
        "columns" -> table.columns
      ).omitEmpty
    }

    implicit val schemaWrites = new Writes[Schema] {
      def writes(schema: Schema) = Json.obj(
        "tables" -> schema.tables
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
        |  ${graphNodes}
        |  ${graphRelations}
        |}
      """.stripMargin.trim
    graph
  }
}

class Scope(val parent: Scope) extends scala.collection.mutable.HashMap[String, String] {
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

abstract class Identifier(name: String) {
  var aliases: Seq[String]
  var traces: Seq[Trace]

  def addTrace(trace: Trace) {
    if(traces == null){
      traces = Seq[Trace](trace)
    } else {
      traces = traces :+ trace
    }
  }
}

class Table (var name: String) extends Identifier(name) {
  var columns: Seq[Column] = Seq[Column]()

  override var aliases: Seq[String] = _
  override var traces: Seq[Trace] = _

  def addColumn(name: String): Column = {
    val existingColumn = columnByName(name)
    if (existingColumn isDefined) {
      return existingColumn.get
    }

    val column = new Column(name)
    columns =  columns :+ column
    column
  }

  def columnByName(name: String): Option[Column] = {
    if(columns isEmpty) return Option.empty[Column]
    columns.find(_.name == name)
  }
}

class Column(var name: String) extends Identifier(name) {
  override var aliases: Seq[String] = _
  override var traces: Seq[Trace] = _
}

class Trace(var file: String, var line: Int, var column: Int){
}

class Relation(var table1: String, var field1: String, var table2: String, var field2: String){
}
