package code

import org.scalatest._

class MainSpec extends WordSpec with Matchers {
  "Parser.parseLine" should {
    "parse a mixed line of frames" in {
      val actual = Parser.parseLine("X 8/ 72 7- --")
      val expected = List(10, 8, 2, 7, 2, 7, 0, 0, 0)
      actual shouldBe expected
    }
  }

  "Scorer.scoreLine" should {
    "score a line of regular frames" in {
      val actual = Scorer.scoreLine(Parser.parseLine("9- 9- 9- 9- 9- 9- 9- 9- 9- 9-"))
      val expected = 10*9
      actual shouldBe expected
    }

    "score a line of spares" in {
      val actual = Scorer.scoreLine(Parser.parseLine("5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5"))
      val expected = 15*9 + 15
      actual shouldBe expected
    }

    "score a line of strikes" in {
      val actual = Scorer.scoreLine(Parser.parseLine("X X X X X X X X X X X X"))
      val expected = 300
      actual shouldBe expected
    }
  }

  "Formatter.formatLine" should {
    "format a mixed line of frames" in {
      val actual = Formatter.formatLine(List(10, 8, 2, 7, 2, 7, 0, 0, 0))
      val expected = "X 8/ 72 7- --"
      actual shouldBe expected
    }
  }
}
