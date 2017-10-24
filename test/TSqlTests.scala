import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import grammars.tsql.{TSqlLexer, TSqlParser, TSqlSelectListVisitor}
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

    val vis = new TSqlSelectListVisitor()
    val ctx = vis.visitSelect_list(
      parser.
        tsql_file().
        batch(0).
        sql_clauses().
        sql_clause(0).
        dml_clause().
        select_statement().
        query_expression().
        query_specification().
        select_list()
    )

    "A" should equal (ctx.fields.head)
  }

  test("multipleFields") {
    val parser = getParser("SELECT A, B, C FROM D;")

    val vis = new TSqlSelectListVisitor()
    val ctx = vis.visitSelect_list(
      parser.
        tsql_file().
        batch(0).
        sql_clauses().
        sql_clause(0).
        dml_clause().
        select_statement().
        query_expression().
        query_specification().
        select_list()
    )

    Set("A", "B", "C") should equal (ctx.fields)
  }
}
