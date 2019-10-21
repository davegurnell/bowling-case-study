package code

import org.scalatest._

class MainSpec extends WordSpec with Matchers {
  "Parser.parseFrame" should {
    "parse a strike" in {
      val actual = Parser.parseFrame("X")
      val expected = Frame(10, None)
      actual shouldBe expected
    }

    "parse a spare" in {
      val actual = Parser.parseFrame("8/")
      val expected = Frame(8, Some(2))
      actual shouldBe expected
    }

    "parse two hits" in {
      val actual = Parser.parseFrame("72")
      val expected = Frame(7, Some(2))
      actual shouldBe expected
    }

    "parse a hit and a miss" in {
      val actual = Parser.parseFrame("7-")
      val expected = Frame(7, Some(0))
      actual shouldBe expected
    }

    "parse two misses" in {
      val actual = Parser.parseFrame("--")
      val expected = Frame(0, Some(0))
      actual shouldBe expected
    }

    "parse a triple-stike final frame" in {
      val actual = Parser.parseFrame("XXX")
      val expected = Frame(10, Some(10), Some(10))
      actual shouldBe expected
    }

    "parse a two-stike final frame" in {
      val actual = Parser.parseFrame("XX9")
      val expected = Frame(10, Some(10), Some(9))
      actual shouldBe expected
    }

    "parse a spare final frame" in {
      val actual = Parser.parseFrame("9/9")
      val expected = Frame(9, Some(1), Some(9))
      actual shouldBe expected
    }

    "parse a spare final frame with a miss" in {
      val actual = Parser.parseFrame("9/-")
      val expected = Frame(9, Some(1), Some(0))
      actual shouldBe expected
    }
  }

  "Parser.parseLine" should {
    "parse a mixed line of frames" in {
      val actual = Parser.parseLine("X 8/ 72 7- --")
      val expected = List(
        Frame(10, None),
        Frame(8, Some(2)),
        Frame(7, Some(2)),
        Frame(7, Some(0)),
        Frame(0, Some(0)),
      )
      actual shouldBe expected
    }
  }

  "Frame.isStrike" should {
    "return true for a strike" in {
      Frame(10, None).isStrike shouldBe true
    }

    "return false for a spare" in {
      Frame(9, Some(1)).isStrike shouldBe false
    }

    "return false for a regular frame" in {
      Frame(8, Some(1)).isStrike shouldBe false
    }
  }

  "Frame.isSpare" should {
    "return false for a strike" in {
      Frame(10, None).isSpare shouldBe false
    }

    "return true for a spare" in {
      Frame(9, Some(1)).isSpare shouldBe true
    }

    "return false for a regular frame" in {
      Frame(8, Some(1)).isSpare shouldBe false
    }
  }

  "Scorer.scoreFrame" should {
    "score a strike" in {
      val actual = Scorer.scoreFrame(Frame(10, None), List(8, 2))
      val expected = 20
      actual shouldBe expected
    }

    "score a spare" in {
      val actual = Scorer.scoreFrame(Frame(9, Some(1)), List(8, 2))
      val expected = 18
      actual shouldBe expected
    }

    "score a regular frame" in {
      val actual = Scorer.scoreFrame(Frame(8, Some(1)), List(8, 2))
      val expected = 9
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
      val actual = Scorer.scoreLine(Parser.parseLine("X X X X X X X X X XXX"))
      val expected = 300
      actual shouldBe expected
    }
  }
}
