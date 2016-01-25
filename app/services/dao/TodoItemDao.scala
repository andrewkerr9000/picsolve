package services.dao

import java.util.concurrent.Executors

import com.google.inject.Singleton
import models.{TodoItemCreate, TodoItem}
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
private[services] class TodoItemDao {
  def init() = {
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")

    DB autoCommit { session: DBSession =>
     session.update("""
      create table if not exists todo_items(
      id int not null primary key auto_increment,
      priority int not null,
      description varchar(100),
      is_done boolean
     )""")
    }
  }

  init()

  implicit val execContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

  def list: Future[Seq[TodoItem]] = Future {
    DB autoCommit { session: DBSession =>
      session.list("select * from todo_items") { rs => TodoItemSQL(rs) }
    }

  }

  def create(todoItemCreate: TodoItemCreate): Future[Unit] = Future {
    DB autoCommit { implicit session: DBSession =>
      sql"insert into todo_items (priority, description, is_done) values (${todoItemCreate.priority}, ${todoItemCreate.description}, ${todoItemCreate.isDone})".execute().apply()
    }
  }
}

object TodoItemSQL extends SQLSyntaxSupport[TodoItem] {
  override val tableName = "todo_items"
  def apply(rs: WrappedResultSet) = new TodoItem(
    rs.long("id"), rs.int("priority"), rs.string("description"), rs.boolean("is_done")
  )
}
