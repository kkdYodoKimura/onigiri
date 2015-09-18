package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Riceball(id: Long, name: String, storeId: Int, description: String)

object Riceball {

  val riceball = {
    get[Long]("id") ~ 
    get[String]("name") ~
    get[Int]("store_id") ~
    get[String]("description") map {
      case id~name~storeId~description => Riceball(id, name, storeId, description)
    }
  }

  def all(): List[Riceball] = DB.withConnection { implicit c =>
    SQL("select * from riceball").as(riceball *)
  }

  def list(page: Int, pageLength: Int): List[Riceball] = DB.withConnection { implicit c =>
    SQL("select * from riceball order by id limit " + pageLength.toString
          + " offset " + ((page - 1) * pageLength).toString).as(riceball *)
  }

  def count: Int =  DB.withConnection { implicit c =>
    SQL("select count(*) from riceball").as(scalar[Int].single)
  }
  
  def select(id: Long): Riceball = DB.withConnection { implicit c =>
    SQL("select * from riceball where id = {id}").on('id -> id).as(riceball *).head
  }

  def search(keyword: String, storeId: Int): List[Riceball] = DB.withConnection { implicit c =>
    require(keyword.length > 0 || storeId != 0) // どちらかは必ず指定
    val storeCond   =
      if(storeId != 0) "store_id = " + storeId.toString
      else ""
    val keywordCond =
      if(keyword.length != 0) "(name like '%" + keyword + "%' or description like '%" + keyword +"%')"
      else ""
    val and =
      if(storeCond.length != 0 && keywordCond.length != 0) " and "
      else ""
    val sql = "select * from riceball where " + storeCond + and + keywordCond
    SQL(sql).as(riceball *)
  }

  def create(name: String, storeId: Int, description: String) {
    DB.withConnection { implicit c =>
      SQL("insert into riceball (name, store_id, description) values ({name}, {store_id}, {description})").on(
        'name -> name,
        'store_id -> storeId,
        'description -> description
      ).executeUpdate()
    }
  }

  def update(id: Long, name: String, storeId: Int, description: String) {
    DB.withConnection { implicit c =>
      SQL("update riceball set name = {name}, store_id = {store_id}, description = {description} where id = {id}").on(
        'id -> id,
        'name -> name,
        'store_id -> storeId,
        'description -> description
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
