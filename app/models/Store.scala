package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Store(id: Int, name: String)

object Store {

  val store = {
    get[Int]("id") ~
    get[String]("name") map {
      case i~n => Store(i, n)
    }
  }

  def all(): List[Store] = DB.withConnection { implicit c =>
    SQL("select * from store").as(store *)
  }

  def select(id: Int): Store = DB.withConnection { implicit c =>
    SQL("select * from store where id = {id}").on('id -> id).as(store *).head
  }
}
