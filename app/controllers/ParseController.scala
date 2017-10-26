package controllers

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import javax.inject._

import grammars.tsql.{TSqlFileVisitor, TSqlLexer, TSqlParser}
import org.antlr.v4.runtime._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ParseController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def getParser(statements: String): TSqlParser = {
    val statementsUpper = statements.toUpperCase
    val stream = new ByteArrayInputStream(statementsUpper.getBytes(StandardCharsets.UTF_8))
    val lexer = new TSqlLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8))
    val tokens = new CommonTokenStream(lexer)
    new grammars.tsql.TSqlParser(tokens)
  }
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    var sqlForm = Form(
      mapping(
        "sql" -> text
      )(sqlFormData.apply)(sqlFormData.unapply)
    )
    sqlForm.bindFromRequest.fold(
      formWithErrors => {
        println("error")
        println(formWithErrors.toString)
        BadRequest(views.html.parse(formWithErrors, 0, ""))
      },
      sql => {
        println("ok")
        println(sql.sql)
        sqlForm = sqlForm.fill(sql)
        val parser = getParser(sql.sql)

        val vis = new TSqlFileVisitor("file")
        val schema = vis.getSchema(parser)

        println(schema.marshallJson())
//        val contactId = Contact.save(contact)
//        Redirect(routes.Application.showContact(contactId)).flashing("success" -> "Contact saved!")

        Ok(views.html.parse(sqlForm, schema.tables.length, schema.marshallJson()))
      }
    )

//    Redirect(controllers.routes.ParseController.index())
//    Ok(views.html.parse(sqlForm, schema.tables.length))
  }
}

case class sqlFormData(sql: String)
