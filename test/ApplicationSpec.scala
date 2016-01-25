import models.TodoItem
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "return empty list if no todo items" in new WithApplication {
      val result = route(FakeRequest(GET, "/api/v1/todoItem")).get

      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "application/json")
      contentAsJson(result).as[Seq[TodoItem]] must beEmpty
    }

    "create and return a todo item" in new WithApplication {
      todo
    }

    "create and return multiple todo items" in new WithApplication {
      todo
    }
  }
}
