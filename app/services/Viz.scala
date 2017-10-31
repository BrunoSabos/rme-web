package services

import java.io.{BufferedInputStream, ByteArrayInputStream, IOException, _}
import java.nio.charset.StandardCharsets


class Viz {
  def getSvg(graph: String): String = {
    try {
//      val dotPath = "/usr/bin/dot"
//      val dotFile = new File(dotPath)
//      val arg1 = dotFile.getAbsolutePath
//      val arg2 = dotPath + ".png"
      val processArgs = Array("dot", "-Tsvg")//, "-o", arg2)
      val process = Runtime.getRuntime.exec(processArgs)

      val outputStream: ByteArrayOutputStream = new ByteArrayOutputStream()
      val output: OutputStream = new BufferedOutputStream(process.getOutputStream)
      val errors = new BufferedInputStream(process.getErrorStream)
      val input = new BufferedInputStream(process.getInputStream)

      val buffer = new Array[Byte](1024)

      val streamInput = new ByteArrayInputStream(new String(graph.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).getBytes())
      try {
        val buf = new Array[Byte](1024)
        var length = streamInput.read(buf)
        while (length != -1) {
          output.write(buf, 0, length) // dotOutput.toByteArray());
          length = streamInput.read(buf)
        }
        // close the stream, or graphviz will read for ever
        output.close()
      } catch {
        case e: IOException =>
          return "error copy graph to graphviz "+e.toString
      }

      import java.io.IOException
      val eout = new ByteArrayOutputStream()

      try {
        var read = errors.read(buffer)
        while ( {
          read != -1
        }) {
          eout.write(buffer, 0, read)
          read = errors.read(buffer)
        }
        val tmp = eout.toString
        //        val log = Logger.getLogger(classOf[Nothing])
        // if there was no outputStream this could be because EOF was reached due to normal end.
                if (tmp.nonEmpty) {
                  return "error from graphviz" + eout.toString
                }
      } catch {
        case _: IOException =>

        // an IO Exception here is the expected outcome
        // we are reading stderr, and there should be no outputStream to read
        // so this thread should (input the normal case) just hang on the read,
        // and then be interrupted when the stderr is closed as part of normal
        // processing.
      }

      try {
        var read = input.read(buffer)
        while ( {
          read != -1
        }) {
          try
            outputStream.write(buffer, 0, read)
          catch {
            case e: Throwable =>
              return "Exception while writing result read from graphviz"+e.toString
          }
          //          cancel.assertContinue
          read = input.read(buffer)
        }
        // close the input - we are finished
        input.close()
        // flag that we are done reading input a normal way.
        //        done = true
      } catch {
        case e: IOException =>
          return "error reading outputStream from graphviz "+e.toString
      }

      val dotSvg = new String(outputStream.toByteArray, StandardCharsets.UTF_8)
      val err = process.waitFor
      return dotSvg
    } catch {
      case e1: IOException =>
        return e1.toString
      case e2: InterruptedException =>
        return e2.toString
    }
    ""
  }
}
