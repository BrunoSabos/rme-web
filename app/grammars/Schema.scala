package grammars

import play.api.libs.json._

class Schema (var fileId: String){
  var tables: Seq[Table] = Seq[Table]()
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
