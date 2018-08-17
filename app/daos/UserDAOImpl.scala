package daos

import errors.CustomError
import helpers.{DAOWrapper, FormatWrapper, MongoWrapper}
import javax.inject.{Inject, Singleton}
import models._
import play.api.libs.json.{JsObject, Json}
import play.cache.AsyncCacheApi
import play.modules.reactivemongo.json._
import reactivemongo.api.{Cursor, DefaultDB, ReadPreference}
import reactivemongo.play.json.collection.JSONCollection
import play.api.http.Status._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * DAO layer implementation for User
  */

@Singleton
class UserDAOImpl @Inject()
(val cache : AsyncCacheApi,mongoWrapper: MongoWrapper) extends UserDAO with DAOWrapper with FormatWrapper {
  import mongoWrapper._

  private final val collectionName = "USER"

  override def add(user: User): Future[Try[User]] = {
    run { defaultDB: DefaultDB =>
      defaultDB.collection[JSONCollection](collectionName)
        .insert(user)
    } map { _ =>
      Success(user)
    } recover {
      case ex : Exception =>
        Failure(ex)
    }
  }

  override def update(id: String, User: User): Future[Try[User]] = {
    val query = Json.obj("id" -> id)
    this.get(id) flatMap {
      case Some(resourceE) =>
        val result = merge(resourceE.asInstanceOf[Product], User.asInstanceOf[Product])
        result match {
          case Success(mergedProduct) =>
            run { defaultDB: DefaultDB =>
              defaultDB.collection[JSONCollection](collectionName)
                .update(query, mergedProduct.asInstanceOf[User])
            } map { _ =>
              Success(mergedProduct.asInstanceOf[User])
            } recover {
              case e : Throwable => Failure(e)
            }
          case Failure(e) =>
            Future(Failure(e))
        }
      case None =>
        val errorCode = 40401
        Future(Failure(CustomError(NOT_FOUND,errorCode,"User not found")))
    }
  }

  override def delete(id: String): Future[Try[String]] = {
    val query = Json.obj()
    run { defaultDB: DefaultDB =>
      defaultDB.collection[JSONCollection](collectionName)
        .findAndRemove(query)
    } map { _ =>
      Success(OK.toString)
    } recover {
      case e : Throwable => Failure(e)
    }
  }

  override def get(id: String): Future[Option[User]] = {
    val query = Json.obj("id" -> id)
    run { defaultDB: DefaultDB =>
      defaultDB.collection[JSONCollection](collectionName)
        .find(query)
        .one[User](ReadPreference.nearest)
    } map { res =>
      res.asInstanceOf[Option[User]]
    }
  }

  override def findOne(query: JsObject,projection : JsObject = Json.obj()): Future[Option[User]] = {
    run { defaultDB: DefaultDB =>
      defaultDB.collection[JSONCollection](collectionName)
        .find(query,projection)
        .one[User](ReadPreference.nearest)
    } map { res =>
      res.asInstanceOf[Option[User]]
    }
  }

  override def find(query: JsObject,projection : JsObject = Json.obj()): Future[List[User]] = {
    run { defaultDB: DefaultDB =>
      defaultDB.collection[JSONCollection](collectionName)
        .find(query,projection)
        .cursor[User](ReadPreference.primary).collect[List]()
    } map { res =>
      res.asInstanceOf[List[User]]
    }
  }

  override def getAll: Future[List[User]] = {
    run { defaultDB: DefaultDB =>
      defaultDB.collection[JSONCollection](collectionName)
        .find(Json.obj())
        .cursor[User](ReadPreference.primary).collect[List]()
    } map { res =>
      res.asInstanceOf[List[User]]
    }
  }
}
