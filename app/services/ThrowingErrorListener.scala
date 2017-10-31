package services

import org.antlr.v4.runtime.misc.ParseCancellationException
import org.antlr.v4.runtime.{BaseErrorListener, RecognitionException, Recognizer}

class ThrowingErrorListener extends BaseErrorListener {
  private var errors: Seq[ParseError] = Seq[ParseError]()
  def getErrors: Seq[ParseError] = errors

  override def syntaxError(recognizer: Recognizer[_, _], offendingSymbol: scala.Any, line: Int, charPositionInLine: Int, msg: String, e: RecognitionException): Unit = {
    errors = errors :+ new ParseError(line, charPositionInLine, msg)
    throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg)
  }
}

class ParseError(var line: Int, var column: Int, var message: String){
  override def toString: String = {
    "line " + line + ":" + column + " " + message
  }
}
