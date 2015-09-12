package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Riceball(id: Long, name: String)

object Riceball {

  val riceball = {
    get[Long]("id") ~ 
    get[String]("name") map {
      case id~name => Riceball(id, name)
    }
  }

  def all(): List[Riceball] = DB.withConnection { implicit c =>
    SQL("select * from riceball").as(riceball *)
  }
  
  def select(id: Long): Riceball = DB.withConnection { implicit c =>
    SQL("select * from riceball where id = {id}").on('id -> id).as(riceball *).head
  }

  def create(name: String) {
    DB.withConnection { implicit c =>
      SQL("insert into riceball (name) values ({name})").on(
        'name -> name
      ).executeUpdate()
    }
  }
  
  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from riceball where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }
}
