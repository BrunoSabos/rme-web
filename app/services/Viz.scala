package services

import java.io.{BufferedInputStream, ByteArrayInputStream, IOException, _}
import java.nio.charset.StandardCharsets


class Viz {
  def getSvg(graph: String): String = {
    try {
      val fileDot = "/usr/bin/dot"
      val f = new File(fileDot)
      val arg1 = f.getAbsolutePath
      val arg2 = arg1 + ".png"
      val c = Array("dot", "-Tsvg")//, "-o", arg2)
      val p = Runtime.getRuntime.exec(c)
      val output: ByteArrayOutputStream = new ByteArrayOutputStream()
      var out: OutputStream = new BufferedOutputStream(p.getOutputStream)
      var errs = new BufferedInputStream(p.getErrorStream)

      var in = new BufferedInputStream(p.getInputStream)
      var buffer = new Array[Byte](1024)

      //      var test = new String("Sécurité".getBytes())

      //      var rx = """[\s\n]""".r
      //      graph = rx.replaceAllIn(graph, "")

      val streamInput = new ByteArrayInputStream(new String(graph.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).getBytes())
      try {
        var buf = new Array[Byte](1024)
        var length = streamInput.read(buf)
        while (length != -1) {
          out.write(buf, 0, length) // dotOutput.toByteArray());
          length = streamInput.read(buf)
        }
        // close the stream, or graphviz will read for ever
        out.close()
      } catch {
        case e: IOException =>
          return "error copy graph to graphviz "+e.toString
      }

      import java.io.IOException
      var eout = new ByteArrayOutputStream()

      try {
        var read = errs.read(buffer)
        while ( {
          read != -1
        }) {
          eout.write(buffer, 0, read)
          read = errs.read(buffer)
        }
        val tmp = eout.toString
        //        val log = Logger.getLogger(classOf[Nothing])
        // if there was no output this could be because EOF was reached due to normal end.
                if (tmp.nonEmpty) {
                  return "error from graphviz" + eout.toString
                }
      } catch {
        case e: IOException =>

        // an IO Exception here is the expected outcome
        // we are reading stderr, and there should be no output to read
        // so this thread should (in the normal case) just hang on the read,
        // and then be interrupted when the stderr is closed as part of normal
        // processing.
      }

      try {
        var read = in.read(buffer)
        while ( {
          read != -1
        }) {
          try
            output.write(buffer, 0, read)
          catch {
            case e: Throwable =>
              return "Exception while writing result read from graphviz"+e.toString
          }
          //          cancel.assertContinue
          read = in.read(buffer)
        }
        // close the input - we are finished
        in.close
        // flag that we are done reading in a normal way.
        //        done = true
      } catch {
        case e: IOException =>
          return "error reading output from graphviz "+e.toString
      }

      val dotSvg = new String(output.toByteArray, StandardCharsets.UTF_8)
      val err = p.waitFor
      return dotSvg
    } catch {
      case e1: IOException =>
        return e1.toString
      case e2: InterruptedException =>
        return e2.toString
    }
    return ""
  }
}
