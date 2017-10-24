import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import grammars.tsql.{TSqlFileVisitor, TSqlLexer, TSqlParser, TSqlSelectListVisitor}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.scalatest._
import org.scalatest.matchers._

class TSqlTests extends FunSuite with Matchers {
//  var parser: TSqlParser

//  before {
//  }

  def getParser(statements: String): TSqlParser = {
    val stream = new ByteArrayInputStream(statements.getBytes(StandardCharsets.UTF_8))
    val lexer = new TSqlLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8))
    val tokens = new CommonTokenStream(lexer)
    new grammars.tsql.TSqlParser(tokens)
  }

  test("oneField") {
    val parser = getParser("SELECT A FROM B;")

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    1 should equal (schema.tables.length)
    val table = schema.tables.head
    "B" should equal (table.name)
    "file" should equal (table.traces.head.file)
    1 should equal (table.traces.head.line)
    14 should equal (table.traces.head.column)
    1 should equal (table.columns.length)
    val column = table.columns.head
    "A" should equal (column.name)
    "file" should equal (column.traces.head.file)
    1 should equal (column.traces.head.line)
    7 should equal (column.traces.head.column)
  }

  test("multipleFields") {
    val parser = getParser("SELECT A, B, C FROM D;")

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    1 should equal (schema.tables.length)
    val table = schema.tables.head
    "D" should equal (table.name)
    3 should equal (table.columns.length)
    Set("A", "B", "C") should equal (table.columns.map(_.name).toSet)
  }
}
