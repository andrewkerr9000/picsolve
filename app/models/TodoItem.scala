package models

import play.api.libs.json.Json

case class TodoItem(id: Long, priority: Int, description: String, isDone: Boolean)

object TodoItem {
  implicit val todoItemFormat = Json.format[TodoItem]
}