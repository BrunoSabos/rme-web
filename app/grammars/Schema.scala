package grammars

class Schema (var fileId: String){
  var tables: Seq[Table] = Seq[Table]()

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