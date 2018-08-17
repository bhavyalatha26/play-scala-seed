package controllers
import errors.CustomError
import helpers.FormatWrapper
import javax.inject._
import models.User
import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Controller to manage the user actions
  * @param cc controller components
  * @param userService user service object
  */
@Singleton
class UserController @Inject()
(cc: ControllerComponents,
 userService: UserService) extends AbstractController(cc) with FormatWrapper {

  private val logger = Logger(this.getClass)

  /*
     Action to add a user
   */
  def add: Action[JsValue] = Action.async(cc.parsers.json) { implicit request =>
    request.body.validate[User].fold(
      invalid => {
        Future(
          BadRequest(Json.toJson(Json.obj(
            "status" -> BAD_REQUEST,
            "code" -> 40002,
            "message" -> "Invalid Request",
            "detailedMessage" -> invalid.seq.toString()
          ))))
      },
      user => {
        userService.add(user) map {
          case Success(newUser) =>
            Ok(Json.toJson(newUser))
          case Failure(e) =>
            e match {
              case custom : CustomError =>
                custom.status match {
                  case NOT_FOUND => NotFound(Json.toJson(custom))
                  case INTERNAL_SERVER_ERROR => InternalServerError(Json.toJson(custom))
                  case BAD_REQUEST | _ => BadRequest(Json.toJson(custom))
                }
              case _ => errorAsResult(e)
            }
        } recover {
          case e : Throwable =>
            logger.error("Error while adding the user : " + e.getMessage)
            errorAsResult(e)
        }
      }
    )
  }

  /*
     Action to get all users
   */
  def getAll: Action[AnyContent] = Action.async { implicit request =>
    userService.getAll map { users =>
      Ok(Json.toJson(users))
    } recover {
      case e : Throwable =>
        logger.error("Error while fetching all the users : " + e.getMessage)
        errorAsResult(e)
    }
  }

  /*
     Action to get a user by id
   */
  def get(id:String) : Action[AnyContent] = Action.async { implicit request =>
    userService.get(id) map {
      case Success(user) =>
        Ok(Json.toJson(user))
      case Failure(e) =>
        e match {
          case custom : CustomError =>
            custom.status match {
              case NOT_FOUND => NotFound(Json.toJson(custom))
              case INTERNAL_SERVER_ERROR => InternalServerError(Json.toJson(custom))
              case BAD_REQUEST | _ => BadRequest(Json.toJson(custom))
            }
          case _ => errorAsResult(e)
        }
    } recover {
      case e : Throwable =>
        logger.error("Error while fetching a user by its id : " + e.getMessage)
        errorAsResult(e)
    }
  }

  /*
     Action to update a user by id
 */
  def update(id : String): Action[JsValue] = Action.async(cc.parsers.json) { implicit request =>
    request.body.validate[User].fold(
      invalid => {
        Future(
          BadRequest(Json.toJson(Json.obj(
            "status" -> BAD_REQUEST,
            "code" -> 40002,
            "message" -> "Invalid Request",
            "detailedMessage" -> invalid.seq.toString()
          ))))
      },
      user => {
        userService.update(id,user) map {
          case Success(updatedUser) =>
            Ok(Json.toJson(updatedUser))
          case Failure(e) =>
            e match {
              case custom : CustomError =>
                custom.status match {
                  case NOT_FOUND => NotFound(Json.toJson(custom))
                  case INTERNAL_SERVER_ERROR => InternalServerError(Json.toJson(custom))
                  case BAD_REQUEST | _ => BadRequest(Json.toJson(custom))
                }
              case _ => errorAsResult(e)
            }
        } recover {
          case e : Throwable =>
            logger.error("Error while updating a user by its id : " + e.getMessage)
            errorAsResult(e)
        }
      }
    )
  }

  /*
   Action to delete a user by id
  */
  def delete(id:String) : Action[AnyContent] = Action.async { implicit request =>
    userService.delete(id) map {
      case Success(successMsg) =>
        Ok(Json.toJson(Json.obj("message" -> s"$successMsg : User successfully deleted")))
      case Failure(e) =>
        e match {
          case custom : CustomError =>
            custom.status match {
              case NOT_FOUND => NotFound(Json.toJson(custom))
              case INTERNAL_SERVER_ERROR => InternalServerError(Json.toJson(custom))
              case BAD_REQUEST | _ => BadRequest(Json.toJson(custom))
            }
          case _ => errorAsResult(e)
        }
    } recover {
      case e : Throwable =>
        logger.error("Error while deleting a user by its id : " + e.getMessage)
        errorAsResult(e)
    }
  }

  private def errorAsResult(e:Throwable) = {
    InternalServerError(Json.toJson(Json.obj(
      "status" -> INTERNAL_SERVER_ERROR,
      "code" -> 50001,
      "message" -> e.getMessage,
      "detailedMessage" -> e.getCause.getMessage
    )))
  }

}
