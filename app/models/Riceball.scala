package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Riceball(id: Long, name: String, store: String, description: String)

object Riceball {

  val riceball = {
    get[Long]("id") ~ 
    get[String]("name") ~
    get[String]("store") ~
    get[String]("description") map {
      case id~name~store~description => Riceball(id, name, store, description)
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
      SQL("insert into riceball (name, store, description)
             values ({name}, {store}, {description})"
      ).on(
        'name -> name,
        'store -> "famima",
        'description -> "oisii"
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
