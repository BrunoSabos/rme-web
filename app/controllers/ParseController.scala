package controllers

import java.io.File
import javax.inject._

import grammars.Schema
import grammars.tsql.TSqlFileVisitor
import play.api.data.Forms._
import play.api.data._
import play.api.http.ContentTypes
import play.api.mvc._
import play.api.libs.json.Json
import services.Viz

import scala.collection.immutable

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ParseController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

//  val dir = "/home/mickael/work/vp/rme-web/db/"
  val dir = "/data/work/vp/dev/tsqlcontrolsource/"
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

  def databasesList = Action { implicit request =>
    // todo conf
    val dirs = getListOfDirs(dir).flatMap(d => getListOfDirs(d.getAbsolutePath).map(sd =>
      Json.obj(
        "server" -> d.getName,
        "database" -> sd.getName
      )
    ))
    Ok(Json.toJson(dirs)).as(ContentTypes.JSON)
  }

  def programmabilitySPList(serverName: String, databaseName: String) = Action { implicit request =>
    // todo conf
    val dir = this.dir+serverName+"/"+databaseName+"/Programmability/Stored Procedures"
//    println("=======")
//    println(dir)
    val dirs = getListOfFiles(dir).map(_.getName).sorted

    val sps:immutable.Map[String, String] = dirs.map(file => {
      val source = scala.io.Source.fromFile(dir + "/" + file)
      val sql = try source.mkString finally source.close()
      file -> sql
    }).toMap


//    println(dirs)
    //    println(Json.toJson(dirs))
    //    println(Json.stringify(Json.toJson(dirs)))

    Ok(Json.toJson(sps)).as(ContentTypes.JSON)
  }

  def getListOfDirs(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(f => f.isDirectory && !f.getName.startsWith(".")).toList
    } else {
      List[File]()
    }
  }

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }
}

case class sqlFormData(sql: String)
