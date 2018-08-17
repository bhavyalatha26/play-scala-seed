package daos

import models.User
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.Future
import scala.util.Try

/**
  * DAO Layer for User
  */
trait UserDAO {
  def add(user:User) : Future[Try[User]]
  def update(id:String,user:User) : Future[Try[User]]
  def delete(id:String) : Future[Try[String]]
  def get(id:String) : Future[Option[User]]
  def getAll : Future[List[User]]
  def find(query: JsObject,projection : JsObject = Json.obj()): Future[List[User]]
  def findOne(query: JsObject,projection : JsObject = Json.obj()): Future[Option[User]]
}
