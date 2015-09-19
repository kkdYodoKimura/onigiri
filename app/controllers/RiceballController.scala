package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import models._

class RiceballController extends Controller {

  val pageLength = 20

  val riceballForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "storeId" -> number,
      "imgurl" -> text,
      "description" -> text
    )
  )

  val searchform = Form(tuple("keyword" -> text, "storeId" -> number))

  def index = Action {
    Redirect(routes.RiceballController.list(1))
  }

  def list(page: Int) = Action {
    Ok(views.html.riceball(Riceball.list(page, pageLength),
                           Riceball.count,
                           page,
                           pageLength,
                           Store.all,
                           searchform))
  }

  def detail(id: Long) = Action {
    val riceball = Riceball.select(id)
    Ok(views.html.detail(riceball, Store.select(riceball.storeId)))
  }
  
  def formAdd = Action {
    Ok(views.html.formAdd(riceballForm, Store.all))
  }

  def add = Action { implicit request =>
    riceballForm.bindFromRequest.fold(
      errors => BadRequest(views.html.error("値が異常です。")),
      data => {
        Riceball.create(data._1, data._2, data._3, data._4)
        Redirect(routes.RiceballController.list(1))
      }
    )
  }

  def formModify(id: Long) = Action {
    val riceball = Riceball.select(id)
    Ok(views.html.formModify(riceball, riceballForm, Store.all))
  }

  def modify(id: Long) = Action { implicit request =>
    riceballForm.bindFromRequest.fold(
      errors => BadRequest(views.html.error("値が異常です。")),
      data => {
        Riceball.update(id, data._1, data._2, data._3, data._4)
        Redirect(routes.RiceballController.detail(id))
      }
    )
  }
  
  def delete(id: Long) = Action {
    Riceball.delete(id)
    Redirect(routes.RiceballController.list(1))
  }

  def search() = Action { implicit request =>
    searchform.bindFromRequest.fold(
      errors => BadRequest(views.html.error("検索条件が異常です。")),
      data => {
        val hitRiceballs = Riceball.search(data._1, data._2)
        Ok(views.html.searchResult(hitRiceballs, Store.all))
      }
    )
  }

}
