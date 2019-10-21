package code

object Main extends App {
  println("Hello world!")
}

object Parser {
  val Triple = "(.)(.)(.)".r
  val Pair = "(.)(.)".r
  val Digit = "([0-9])".r

  def parseLine(str: String): List[Int] =
    parseFrames(str.replaceAll(" ", "").toList.map(_.toString))

  def parseFrames(chars: List[String]): List[Int] =
    chars match {
      case Nil => Nil
      case "X" :: t             => 10 :: parseFrames(t)
      case "-" :: t             => 0  :: parseFrames(t)
      case Digit(h) :: "/" :: t => h.toInt :: (10 - h.toInt) :: parseFrames(t)
      case Digit(h) :: t        => h.toInt :: parseFrames(t)
    }
}

object Scorer {
  def scoreLine(line: List[Int]): Int =
    line match {
      case Nil                                => 0
      case a :: b :: c :: Nil if strike(a)    => a + b + c
      case a :: b :: c :: Nil if spare(a, b)  => a + b + c
      case a :: b :: c :: rest if strike(a)   => 10 + b + c + scoreLine(b :: c :: rest)
      case a :: b :: c :: rest if spare(a, b) => 10 + c + scoreLine(c :: rest)
      case a :: b :: rest                     => a + b + scoreLine(rest)
    }

  private def strike(a: Int): Boolean =
    a == 10

  private def spare(a: Int, b: Int): Boolean =
    a + b == 10
}

object Formatter {
  def formatLine(line: List[Int]): String = {
    def loop(line: List[Int]): List[String] =
      line match {
        case Nil                   => Nil
        case 10 :: 10 :: 10 :: Nil => List("X", "X", "X")
        case a  :: b  :: c  :: Nil => List(formatPair(a, b), formatRoll(c))
        case 10 :: rest            => "X" +: loop(rest)
        case a  :: b :: rest       => formatPair(a, b) +: loop(rest)
        case line                  => throw new Exception(s"Invalid line: $line")
      }

    loop(line).mkString(" ")
  }

  private def formatPair(a: Int, b: Int): String =
    if(a + b == 10) {
      formatRoll(a) + "/"
    } else {
      formatRoll(a) + formatRoll(b)
    }

  private def formatRoll(n: Int): String =
    n match {
      case 10 => "X"
      case 0  => "-"
      case d  => d.toString
    }
}
