package controllers

import java.io.{ByteArrayInputStream, FileInputStream}
import javax.inject._

import play.api.mvc._
import org.antlr.v4.runtime._
import org.antlr.v4.runtime.tree._
import org.stringtemplate.v4._
import java.io.FileInputStream
import java.nio.charset.StandardCharsets

import grammars.tsql.{TSqlFileVisitor, TSqlLexer, TSqlSelectListVisitor}
import org.antlr.runtime.ANTLRStringStream

import scala.collection.JavaConverters._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ParseController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    val lines = scala.io.Source.fromFile("/data/perso/dev/rme-web/public/data/sample.sql").mkString
//    val input = new ANTLRStringStream(lines)
    val stream = new ByteArrayInputStream(lines.getBytes(StandardCharsets.UTF_8))
    val lexer = new TSqlLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8))
    val tokens = new CommonTokenStream(lexer)
    val parser = new grammars.tsql.TSqlParser(tokens)
    val prog = parser.tsql_file()

    println (prog.toString)

    val vis = new TSqlFileVisitor("file")
    println ("ici")
    var schema = vis.getSchema(parser)
    println ("la")

    Ok(views.html.parse("Parse"))
  }
}
