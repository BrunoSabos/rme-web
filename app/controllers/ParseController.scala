package controllers

import javax.inject._

import grammars.Schema
import grammars.tsql.TSqlFileVisitor
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import services.Viz

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ParseController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

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
        val vis = new TSqlFileVisitor("file")
        vis.getSchema(sql.sql) match {
          case null => BadRequest(vis.getErrors.mkString)
          case schema: Schema =>
            println(schema.marshallJson())
            Ok(views.html.parse(sqlForm, schema.tables.length, schema.marshallJson()))
        }
      }
    )
  }

  def image = Action { implicit request =>
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
        sqlForm = sqlForm.fill(sql)
        val vis = new TSqlFileVisitor("file")
        vis.getSchema(sql.sql) match {
          case null => BadRequest(vis.getErrors.mkString)
          case schema: Schema =>
            val viz = new Viz ()
            val svg = viz.getSvg (schema.marshallGraph())
            Ok (svg).as("image/svg+xml")
        }
      }
    )
  }
}

case class sqlFormData(sql: String)
