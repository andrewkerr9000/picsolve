package models

import play.api.libs.json.Json

case class TodoItemCreate(priority: Int, description: String, isDone: Boolean)

object TodoItemCreate {
  implicit val todoItemFormat = Json.format[TodoItemCreate]
}