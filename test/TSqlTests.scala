import java.io.{ByteArrayInputStream, File}
import java.nio.charset.StandardCharsets

import grammars.tsql.{TSqlFileVisitor, TSqlLexer, TSqlParser}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.scalatest._

import scala.io.Codec

class TSqlTests extends FunSuite with Matchers {

  def getParser(statements: String): TSqlParser = {
    val statementsUpper = statements.toUpperCase
    val stream = new ByteArrayInputStream(statementsUpper.getBytes(StandardCharsets.UTF_8))
    val lexer = new TSqlLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8))
    val tokens = new CommonTokenStream(lexer)
    new grammars.tsql.TSqlParser(tokens)
  }

  test("oneTable") {
    val parser = getParser("SELECT C1 FROM T1;")

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    println(schema.marshallJson())

    schema.tables.length shouldEqual 1
    val table = schema.tables.head
    table.name shouldEqual "T1"
    table.traces.length shouldEqual 2
    table.traces.head.file shouldEqual "file"
    table.traces.head.line shouldEqual 1
    table.traces.head.column shouldEqual 15
    table.traces.last.file shouldEqual "file"
    table.traces.last.line shouldEqual 1
    table.traces.last.column shouldEqual 7

    table.columns.length shouldEqual 1
    val column = table.columns.head
    column.name shouldEqual "C1"
    column.traces.length shouldEqual 1
    column.traces.head.file shouldEqual "file"
    column.traces.head.line shouldEqual 1
    column.traces.head.column shouldEqual 7
  }

  test("joinTwoTables") {
    val parser = getParser(
      """SELECT T1.C2, T2.C3
        |FROM T1
        |INNER JOIN T2
        |ON T1.C1 = T2.C1;""".stripMargin)

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    println(schema.marshallJson())

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

    println(schema.marshallJson())

    schema.tables.length shouldEqual 1
    val table = schema.tables.head
    table.name shouldEqual "T1"
    table.columns.length shouldEqual 3
    table.columns.map(_.name).toSet shouldEqual Set("C1", "C2", "C3")
  }

  test("alias") {
    val parser = getParser("SELECT a.C1, T1.C2, C3 FROM T1 a;")

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    println(schema.marshallJson())

    schema.tables.length shouldEqual 1
    val table = schema.tables.head
    table.name shouldEqual "T1"
    table.columns.length shouldEqual 3
    table.columns.map(_.name).toSet shouldEqual Set("C1", "C2", "C3")
  }

  test("subSelect") {
    val parser = getParser(
      """SELECT Tt.Ca, T1.C2, T2.C4
        |FROM (
        |  SELECT T1.C1 AS Ca, T1.C2
        |  FROM T2
        |) Tt, T2
        |WHERE
        |  Tt.Ca = T2.C3
        |""".stripMargin)

    // T1: C1, C2
    // T2: C3, C4

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    println(schema.marshallJson())

    schema.tables.length shouldEqual 2
    val table1 = schema.tableByName("T1").get
    table1.name shouldEqual "T1"
    table1.columns.length shouldEqual 2
    table1.columns.map(_.name).toSet shouldEqual Set("C1", "C2")

    val table2 = schema.tableByName("T2").get
    table2.name shouldEqual "T2"
    table2.columns.length shouldEqual 2
    table2.columns.map(_.name).toSet shouldEqual Set("C3", "C4")

    schema.relations.length shouldEqual 1
    schema.relations.head.table1 shouldEqual "T1"
    schema.relations.head.field1 shouldEqual "C1"
    schema.relations.head.table2 shouldEqual "T2"
    schema.relations.head.field2 shouldEqual "C3"
  }

  test("subSubSelect") {
    val parser = getParser(
      """SELECT Tt.Ca
        |FROM (
        |  SELECT Tu.C1 AS Ca
        |  FROM (
        |    SELECT C1
        |    FROM T1
        |  ) Tu
        |) Tt, T2
        |WHERE
        |  Tt.Ca = T2.C2
        |""".stripMargin)

    // T1: C1
    // T2: C2

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    println(schema.marshallJson())

    schema.tables.length shouldEqual 2
    val table1 = schema.tableByName("T1").get
    table1.name shouldEqual "T1"
    table1.columns.length shouldEqual 1
    table1.columns.map(_.name).toSet shouldEqual Set("C1")

    val table2 = schema.tableByName("T2").get
    table2.name shouldEqual "T2"
    table2.columns.length shouldEqual 1
    table2.columns.map(_.name).toSet shouldEqual Set("C2")

    schema.relations.length shouldEqual 1
    schema.relations.head.table1 shouldEqual "T1"
    schema.relations.head.field1 shouldEqual "C1"
    schema.relations.head.table2 shouldEqual "T2"
    schema.relations.head.field2 shouldEqual "C2"
  }

  test("columnInConditions") {
    val parser = getParser("""SELECT *
        |FROM T1 a, T2
        |WHERE T1.C1 = 1
        |AND a.C2 = 2
        |AND T2.C3 = T1.C4;""".stripMargin)

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    println(schema.marshallJson())

    schema.tables.length shouldEqual 2
    val table1 = schema.tableByName("T1").get
    table1.columns.length shouldEqual 3
    table1.columns.map(_.name).toSet shouldEqual Set("C1", "C2", "C4")

    val table2 = schema.tableByName("T2").get
    table2.columns.length shouldEqual 1
    table2.columns.map(_.name).toSet shouldEqual Set("C3")
  }

  test("oneShot") {
//    val source = scala.io.Source.fromFile(new File("/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/RPT_OrderDO.sql"))(Codec.ISO8859)
    val source = scala.io.Source.fromFile(new File("/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/IMP_GetSurExp.sql"))(Codec.ISO8859)
//    val source = scala.io.Source.fromFile(new File("/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/P_GetPricing.sql"))(Codec.ISO8859)
    println(source)

    val lines = try source.mkString finally source.close()

    val parser = getParser(lines)

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(parser)

    println(schema.marshallJson())

    println(schema.marshallGraph())
  }
}
