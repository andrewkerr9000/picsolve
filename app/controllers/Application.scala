package controllers

import models.TodoItem
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

class Application extends Controller {

  def list = Action {
    Ok(Json.toJson(Seq[TodoItem]()))
  }

}
