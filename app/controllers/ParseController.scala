package controllers

import java.io.File
import java.nio.file.{Files, Paths}
import javax.inject._

import grammars.Schema
import grammars.tsql.TSqlFileVisitor
import play.api.data.Forms._
import play.api.data._
import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.mvc._
import services.Viz

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
            val viz = new Viz()
            val svg = viz.getSVG(schema.marshallGraph())
            Ok(svg).as("image/svg+xml")
        }
      }
    )
  }

  def imagePost = Action { implicit request =>
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
            val viz = new Viz()
            val svg = viz.getSVG(schema.marshallGraph())
            Ok(svg).as("image/svg+xml")
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
    val dir = this.dir + serverName + "/" + databaseName + "/Programmability/Stored Procedures"
    val spFilenames = getListOfFiles(dir).map(_.getName).sorted

    // Only filename
    val sps = spFilenames.map(s =>
      Json.obj(
        "filename" -> s
      ))

    // Filename with content
    //    val sps:immutable.Map[String, String] = spFilenames.map(file => {
    //      val source = scala.io.Source.fromFile(dir + "/" + file)
    //      val sql = try source.mkString finally source.close()
    //      file -> sql
    //    }).toMap


    //    println(spFilenames)
    //    println(Json.toJson(spFilenames))
    //    println(Json.stringify(Json.toJson(spFilenames)))

    Ok(Json.toJson(sps)).as(ContentTypes.JSON)
  }

  def programmabilitySPGet(serverName: String, databaseName: String, spFilename: String) = Action { implicit request =>
    // todo conf
    val dir = this.dir + serverName + "/" + databaseName + "/Programmability/Stored Procedures"
    val source = scala.io.Source.fromFile(dir + "/" + spFilename)(scala.io.Codec.ISO8859)
    val sql = try source.mkString finally source.close()
//    var sql = new String(Files.readAllBytes(Paths.get(dir + "/" + spFilename)))
    //    println(spFilenames)
    //    println(Json.toJson(spFilenames))
    //    println(Json.stringify(Json.toJson(spFilenames)))

    Ok(sql).as(ContentTypes.TEXT)
  }

  def getListOfDirs(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(f => f.isDirectory && !f.getName.startsWith(".")).toList
    } else {
      List[File]()
    }
  }

  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }
}

case class sqlFormData(sql: String)
