package services

import models.User

import scala.concurrent.Future
import scala.util.Try

/**
  * Service layer for the User Service
  */
trait UserService {
  def add(user: User) : Future[Try[User]]
  def get(id: String) : Future[Try[User]]
  def getAll : Future[List[User]]
  def update(id: String,user: User) : Future[Try[User]]
  def delete(id: String) : Future[Try[String]]
}

