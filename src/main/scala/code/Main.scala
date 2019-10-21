package code

object Main extends App {
  println("Hello world!")
}

case class Frame(first: Int, second: Option[Int], bonus: Option[Int] = None) {
  def isStrike: Boolean =
    first == 10

  def isSpare: Boolean =
    !isStrike && first + second.getOrElse(0) == 10

  def score1: Int =
    first

  def score2: Int =
    second.getOrElse(0)

  def bonusScore: Int =
    bonus.getOrElse(0)

  def rolls: List[Int] =
    first :: second.toList ::: bonus.toList
}

object Parser {
  val Triple = "(.)(.)(.)".r
  val Pair = "(.)(.)".r
  val Digit = "([0-9])".r

  def parseLine(str: String): List[Frame] =
    str.split(" +").toList.map(parseFrame)

  def parseFrame(str: String): Frame =
    str match {
      case Triple("X", "X", "X")           => Frame(10, Some(10), Some(10))
      case Triple("X", "X", "-")           => Frame(10, Some(10), Some(0))
      case Triple("X", "X", Digit(c))      => Frame(10, Some(10), Some(c.toInt))

      case Triple("X", Digit(b), "/")      => Frame(10, Some(b.toInt), Some(10 - b.toInt))
      case Triple("X", Digit(b), "-")      => Frame(10, Some(b.toInt), Some(0))
      case Triple("X", Digit(b), Digit(c)) => Frame(10, Some(b.toInt), Some(c.toInt))

      case Triple(Digit(a), "/", "-")      => Frame(a.toInt, Some(10 - a.toInt), Some(0))
      case Triple(Digit(a), "/", Digit(c)) => Frame(a.toInt, Some(10 - a.toInt), Some(c.toInt))

      case Pair("-", "/")                  => Frame(0, Some(10))
      case Pair("-", "-")                  => Frame(0, Some(0))

      case Pair(Digit(a), "/")             => Frame(a.toInt, Some(10 - a.toInt))
      case Pair(Digit(a), "-")             => Frame(a.toInt, Some(0))
      case Pair(Digit(a), Digit(b))        => Frame(a.toInt, Some(b.toInt))

      case "X" => Frame(10, None)

      case other => throw new Exception(s"Bad frame: $other")
    }
}

object Scorer {
  def scoreLine(line: List[Frame]): Int =
    line match {
      case Nil =>
        0

      case head :: Nil =>
        scoreLastFrame(head)

      case head :: rest =>
        scoreFrame(head, rest.flatMap(_.rolls)) + scoreLine(rest)
    }

  def scoreFrame(curr: Frame, rest: List[Int]): Int =
    if(curr.isStrike) {
      10 + rest.take(2).sum
    } else if(curr.isSpare) {
      10 + rest.take(1).sum
    } else {
      curr.score1 + curr.score2
    }

  def scoreLastFrame(curr: Frame): Int =
    curr.score1 + curr.score2 + curr.bonusScore
}