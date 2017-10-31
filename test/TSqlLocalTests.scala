import java.io.File
import java.nio.file.{Files, Paths}

import grammars.tsql.TSqlFileVisitor
import org.scalatest._

import scala.io.Codec

class TSqlLocalTests extends FunSuite with Matchers {
  test("oneShot") {
    val filepath: String = "/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/P_GetPricing.sql"
    if(Files.exists(Paths.get(filepath))) {
      //    val source = scala.io.Source.fromFile(new File("/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/RPT_OrderDO.sql"))(Codec.ISO8859)
      //    val source = scala.io.Source.fromFile(new File("/data/work/vp/dev/tsqlcontrolsource/LOGSQL02/VPN3/Programmability/Stored Procedures/IMP_GetSurExp.sql"))(Codec.ISO8859)
      val source = scala.io.Source.fromFile(new File(filepath))(Codec.ISO8859)
      //    val source = scala.io.Source.fromFile(new File("/data/perso/dev/rme-core/databases/74610bb80f3e490e8d428fff3ac95ba8/55F1D96BAA717C8D5F5DA6A0CA926B44A8A13F60.sql"))(Codec.UTF8)
      //    val source = scala.io.Source.fromFile(new File("/data/perso/dev/rme-core/databases/89c9145973cc492daad2349fee78920b/b.sql"))(Codec.UTF8)
      println(source)

      val lines = try source.mkString finally source.close()
      val vis = new TSqlFileVisitor("file")
      val schema = vis.getSchema(lines)

      println(schema.marshallJson())
      println(schema.marshallGraph())
    }
  }
}
