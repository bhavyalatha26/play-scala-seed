package controllers

import helpers.FormatWrapper
import javax.inject._
import play.api.mvc._
import services.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Controller to manage the index page
  * @param cc controller components
  */

@Singleton
class HomeController @Inject()
(cc: ControllerComponents) extends AbstractController(cc) with FormatWrapper {

  def index : Action[AnyContent] = Action.async { implicit request =>
    Future(Ok("Welcome Play Scala Demo App"))
  }

}
