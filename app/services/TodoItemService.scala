package services

import com.google.inject.Inject
import models.{TodoItem, TodoItemCreate}
import services.dao.TodoItemDao
import scala.concurrent.Future

class TodoItemService @Inject() (todoItemDao: TodoItemDao) {

  def list: Future[Seq[TodoItem]] = todoItemDao.list

  def create(todoItemCreate: TodoItemCreate): Future[Unit] = todoItemDao.create(todoItemCreate)
}
