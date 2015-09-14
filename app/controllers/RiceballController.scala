package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import models._

class RiceballController extends Controller {

  val riceballForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "storeId" -> number,
      "description" -> text
    )
  )

  def index = Action {
    Redirect(routes.RiceballController.list)
  }

  def list = Action {
    Ok(views.html.riceball(Riceball.all()))
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
      errors => BadRequest(views.html.riceball(Riceball.all())),
      data => {
        Riceball.create(data._1, data._2, data._3)
        Redirect(routes.RiceballController.list)
      }
    )
  }

  def formModify(id: Long) = Action {
    val riceball = Riceball.select(id)
    Ok(views.html.formModify(riceball, riceballForm.fill(
      riceball.name,
      riceball.storeId,
      riceball.description
    ), Store.all))
  }

  def modify(id: Long) = Action { implicit request =>
    riceballForm.bindFromRequest.fold(
      errors => BadRequest(views.html.riceball(Riceball.all())),
      data => {
        Riceball.update(id, data._1, data._2, data._3)
        Redirect(routes.RiceballController.detail(id))
      }
    )
  }
  
  def delete(id: Long) = Action {
    Riceball.delete(id)
    Redirect(routes.RiceballController.list)
  }

}
