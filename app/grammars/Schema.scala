package grammars

class Schema (var fileId: String){
  var tables: Seq[Table] = _

  def addTable(name: String): Table = {
    val table = new Table(name)
    if(tables == null){
      tables = Seq[Table](table)
    } else {
      tables = tables :+ table
    }
    table
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
  var columns: Seq[Column] = _

  override var aliases: Seq[String] = _
  override var traces: Seq[Trace] = _

  def addColumn(name: String): Column = {
    val column = new Column(name)
    if(columns == null){
      columns = Seq[Column](column)
    } else {
      columns = columns :+ column
    }
    column
  }
}

class Column(var name: String) extends Identifier(name) {
  override var aliases: Seq[String] = _
  override var traces: Seq[Trace] = _
}

class Trace(var file: String, var line: Int, var column: Int){
}