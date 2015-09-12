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
  "name" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.RiceballController.list)
  }

  def list = Action {
    Ok(views.html.riceball(Riceball.all(), riceballForm))
  }

  def detail(id: Long) = Action {
    Ok(views.html.detail(Riceball.select(id)))
  }
  
  def add = Action { implicit request =>
    riceballForm.bindFromRequest.fold(
      errors => BadRequest(views.html.riceball(Riceball.all(), errors)),
      name => {
        Riceball.create(name)
        Redirect(routes.RiceballController.list)
      }
    )
  }
  
  def delete(id: Long) = Action {
    Riceball.delete(id)
    Redirect(routes.RiceballController.list)
  }

}
