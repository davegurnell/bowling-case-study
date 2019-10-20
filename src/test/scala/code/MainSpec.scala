package code

import org.scalatest._

class MainSpec extends WordSpec with Matchers {
  "true" should {
    "equal false" in {
      true shouldBe false
    }
  }
}
