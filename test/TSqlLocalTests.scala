import java.io._
import java.nio.file.{Files, Paths}

import grammars.Schema
import grammars.tsql.TSqlFileVisitor
import org.scalatest._
import services.Viz

import util.control.Breaks._
import scala.io.Codec

class TSqlLocalTests extends FunSuite with Matchers {
  test("oneShot") {
    // todo unit test ok table temp DET_GCSIntegrationUnknownProducts
    // todo unit test ok pour table without hint sur RPT_GetOperationSixMonthsAgo
    // todo unit test ok query without from clause OXE_GetSizeFormat
    val spName = "AuditSQL_GenerateScriptForControlSource"
    val filepath: String = "/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/" + spName + ".sql"
    val imagePath: String = "/data/work/vp/dev/tsqlimages/LOGSQL02/VPN3/Programmability/Stored Procedures/" + spName + "_local.png"
    if (Files.exists(Paths.get(filepath))) {
      //    val source = scala.io.Source.fromFile(new File("/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/RPT_OrderDO.sql"))(Codec.ISO8859)
      //    val source = scala.io.Source.fromFile(new File("/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/IMP_GetSurExp.sql"))(Codec.ISO8859)
      val source = scala.io.Source.fromFile(new File(filepath))(Codec.ISO8859)
      //    val source = scala.io.Source.fromFile(new File("/data/perso/dev/rme-core/databases/74610bb80f3e490e8d428fff3ac95ba8/55F1D96BAA717C8D5F5DA6A0CA926B44A8A13F60.sql"))(Codec.UTF8)
      //    val source = scala.io.Source.fromFile(new File("/data/perso/dev/rme-core/databases/89c9145973cc492daad2349fee78920b/b.sql"))(Codec.UTF8)
      println(source)

      val lines = try source.mkString finally source.close()
      val vis = new TSqlFileVisitor("file")
      val schema = vis.getSchema(lines)

      if (schema == null) {
        fail(vis.getErrors.mkString("\n"))
      }

      println(schema.marshallJson())
      println(schema.marshallGraph())
      var viz = new Viz()
      var png = viz.getPNG(schema.marshallGraph())
      writeToFile(png, imagePath)
    }
  }

  test("oneShotString") {
    val query =
      """
        |	SELECT
        |		ax.ItemId,
        |		substring((SELECT ', '+ bc.BarCode
        |		 FROM itemRef.T_BarCodes bc
        |		 WHERE bc.OperationCode = ax.OperationCode AND bc.ItemId = ax.ItemId
        |		 for XML PATH('')), 3, 50) AS BarCodes
        |	FROM
        |		ItemRef.T_ItemsAX ax
      """.stripMargin

    val vis = new TSqlFileVisitor("file")
    val schema = vis.getSchema(query)

    println(schema.marshallJson())
    //    println(schema.marshallGraph())
  }

  test("checkThemAll") {
    val repositoryDir: String = "/data/work/vp/dev/tsqlcontrolsource"
    val imagesDir: String = "/data/work/vp/dev/tsqlimages"
    val repoDir = new File(repositoryDir)
    val visibleDirs: File => Boolean = d => d.isDirectory && !d.getName.startsWith(".")
    val visibleQueries: File => Boolean = d => d.isFile && !d.getName.startsWith(".")
    var schemaGLobal = new Schema("global")
    if (repoDir.exists) {
      val serversDir = repoDir.listFiles().filter(visibleDirs)
        // todo filter
        //.filter(s => s.getName == "ADMINSQL")
      serversDir.foreach(s => {
        println(s"SERVER: ${s.getName}")
        val databasesDir = s.listFiles().filter(visibleDirs)
        databasesDir.foreach(d => {
          println(s"DATABASE: ${d.getName}")
          val spPath = "/Programmability/Stored Procedures"
          val spDir = new File(Paths.get(d.getAbsolutePath, spPath).toString)
          if (spDir.exists) {
            val queries = spDir.listFiles().filter(visibleQueries)
            val nbQueries = queries.length
            var ixQuery = 0
            queries.foreach(q => {
              breakable {
                ixQuery += 1
                println(s"QUERY $ixQuery / $nbQueries: ${q.getName}")

                val queryContentBUffer = scala.io.Source.fromFile(new File(q.getAbsolutePath))(Codec.ISO8859)
                val vis = new TSqlFileVisitor("file")
                val queryContent = try queryContentBUffer.mkString finally queryContentBUffer.close()
                var schema: Schema = null
                try {
                  schema = vis.getSchema(queryContent, logEnabled = false)
                  if(schema == null) {
                    fail("schema == null")
                  }
                }catch{
                  case e: Exception => {
                    println(s"======> ERROR: ${q.getAbsolutePath} - " + e.toString)
                    break
                  }
                }
                if (vis.getErrors.nonEmpty) {
                  // fail(vis.getErrors.mkString(", "))
                  println(s"======> ERROR: ${q.getAbsolutePath} - " + vis.getErrors.mkString(", "))
                  break
                }

                schemaGLobal.merge(schema)

                val viz = new Viz()
                val str = schema.marshallGraph()
                //                println("Get png")
                val png = viz.getPNG(str)
//                println("png ok")
                if (viz.getErrors.nonEmpty) {
                  fail(viz.getErrors.mkString(", "))
                }
                //              println(png)
                val imagePath = Paths.get(imagesDir, s.getName, d.getName, spPath, getFileNameWithoutExtension(q.getName)) + ".png"
                //              println(imagePath)
                writeToFile(png, imagePath)
                //              fail("ok")


                // Global schema
                val gviz = new Viz()
                val gstr = schemaGLobal.marshallGraph()
                val gpng = viz.getPNG(gstr)
                if (gviz.getErrors.nonEmpty) {
                  fail(gviz.getErrors.mkString(", "))
                }
                val gimagePath = Paths.get(imagesDir, s.getName, d.getName, spPath+"/")+"global.png"
                writeToFile(gpng, gimagePath)
              }
            })
          }
        })
      })
    }
  }

  def getFileNameWithoutExtension(fileName: String): String = {
    fileName.dropRight(fileName.length - fileName.lastIndexOf("."))
  }

  def writeToFile(content: Array[Byte], filePath: String): Unit = {
    val bytes = new Array[Byte](1024)
    var input = None: Option[ByteArrayInputStream]
    var output = None: Option[FileOutputStream]

    try {
      val dir = new File(filePath).getParentFile
      if (!dir.exists()) {
        val ok = dir.mkdirs()
        if (!ok) {
          println(s"Can't create ${dir.getAbsolutePath}")
          return
        }
      }
      input = Some(new ByteArrayInputStream(content))
      output = Some(new FileOutputStream(filePath))
      Stream
        .continually(input.get.read(bytes))
        .takeWhile(-1 !=)
        .foreach(read => output.get.write(bytes, 0, read))
    } catch {
      case e: IOException => println(e.printStackTrace)
    } finally {
      //      println("entered finally ...")
      if (input.isDefined) input.get.close
      if (output.isDefined) output.get.close
    }
  }
}
