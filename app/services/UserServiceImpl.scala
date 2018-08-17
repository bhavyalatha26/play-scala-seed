package services

import com.google.inject.Inject
import daos.UserDAO
import errors.CustomError
import javax.inject.Singleton
import models.User
import play.api.Logger
import play.api.http.Status._
import utils.{RandomUUID, Util}
import utils.Util.NOW

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * Service Layer implementation for the User Service
  * @param userDAO user dao object
  */
@Singleton
class UserServiceImpl @Inject()
(userDAO: UserDAO) extends UserService {

  private val logger = Logger(this.getClass)

  override def add(user: User): Future[Try[User]] = {
    logger.debug("[add] Add the given user to the data store")
    userDAO.add(
      user.copy(
        id = Some(RandomUUID.generate),
        creationDateTime = Some(NOW),
        modifiedDateTime = Some(NOW)
      )
    )
  }

  override def get(id: String): Future[Try[User]] = {
    logger.debug("[get] Get the user by id")
    userDAO.get(id) map {
      case Some(user) => Success(user)
      case None =>
        val statusCode = 40401
        Failure(CustomError(NOT_FOUND,statusCode,"User not found !",Option(s"User data not found with given id - $id")))
    }
  }

  override def getAll : Future[List[User]] = {
    logger.debug("[getAll] List all the available users")
    userDAO.getAll
  }

  override def update(id: String,user: User): Future[Try[User]] = {
    logger.debug("[update] Update the given user")
    userDAO.update(id, user.copy(modifiedDateTime = Some(Util.NOW)))
  }

  override def delete(id: String): Future[Try[String]] = {
    logger.debug("[delete] Delete the given user")
    this.get(id) flatMap {
      case Success(_) =>
        userDAO.delete(id)
      case Failure(e) =>
        Future(Failure(e))
    }
  }
}
