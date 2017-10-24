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

  test("oneTable") {
    val parser = getParser("SELECT C1 FROM T1;")

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    schema.tables.length shouldEqual 1
    val table = schema.tables.head
    table.name shouldEqual "T1"
    table.traces.head.file shouldEqual "file"
    table.traces.head.line shouldEqual 1
    table.traces.head.column shouldEqual 15

    table.columns.length shouldEqual 1
    val column = table.columns.head
    column.name shouldEqual "C1"
    column.traces.head.file shouldEqual "file"
    column.traces.head.line shouldEqual 1
    column.traces.head.column shouldEqual 7
  }

  test("joinTwoTables") {
    val parser = getParser("""SELECT T1.C2, T2.C3
FROM T1
INNER JOIN T2
ON T1.C1 = T2.C1;""")

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    println(schema.tables.map(_.name).mkString(", "))

    schema.tables.length shouldEqual 2
    schema.tables.map(_.name).toSet shouldEqual Set("T1", "T2")

    val table1 = schema.tableByName("T1")
    val table2 = schema.tableByName("T2")

    table1 shouldBe defined
    table2 shouldBe defined

    table1.get.traces.length shouldEqual 3

    table1.get.traces.head.file shouldEqual "file"
    table1.get.traces.head.line shouldEqual 2
    table1.get.traces.head.column shouldEqual 5

    table1.get.traces.tail.head.file shouldEqual "file"
    table1.get.traces.tail.head.line shouldEqual 4
    table1.get.traces.tail.head.column shouldEqual 3

    table1.get.traces.tail.tail.head.file shouldEqual "file"
    table1.get.traces.tail.tail.head.line shouldEqual 1
    table1.get.traces.tail.tail.head.column shouldEqual 7

    table1.get.columns.length shouldEqual 2
    val column1 = table1.get.columnByName("C1")

    column1 shouldBe defined

    column1.get.traces.length shouldEqual 1

    column1.get.traces.head.file shouldEqual "file"
    column1.get.traces.head.line shouldEqual 4
    column1.get.traces.head.column shouldEqual 3

    val column2 = table1.get.columnByName("C2")

    column2 shouldBe defined

    column2.get.traces.length shouldEqual 1

    column2.get.traces.head.file shouldEqual "file"
    column2.get.traces.head.line shouldEqual 1
    column2.get.traces.head.column shouldEqual 7
  }

  test("oneTableMultipleColumns") {
    val parser = getParser("SELECT C1, C2, C3 FROM T1;")

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    schema.tables.length shouldEqual 1
    val table = schema.tables.head
    table.name shouldEqual "T1"
    table.columns.length shouldEqual 3
    table.columns.map(_.name).toSet shouldEqual Set("C1", "C2", "C3")
  }
}
