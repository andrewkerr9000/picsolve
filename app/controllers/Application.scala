package controllers

import com.google.inject.Inject
import models.TodoItemCreate
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.TodoItemService
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

class Application @Inject() (todoItemService: TodoItemService) extends Controller {

  def list = Action.async {
    todoItemService.list.map(items => Ok(Json.toJson(items)))
  }

  // Returned data is not specified. I would expect it to at least include the id of the created item.
  def create() = Action.async { implicit request =>
    val item = request.body.asJson.flatMap(_.validate[TodoItemCreate].asOpt)
    item match {
      case None => Future(BadRequest)
      case Some(todoItem) =>
        todoItemService.create(todoItem).map(_ => Ok)
    }
  }

}
