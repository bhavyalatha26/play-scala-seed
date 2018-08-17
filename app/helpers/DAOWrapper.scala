package helpers

import scala.util.{Failure, Success, Try}

/**
  * A wrapper for the DAO that holds all common utilities
  */
trait DAOWrapper {

  def merge(merge: Product, withObj: Product): Try[Product] = {
    try {
      val classType = merge.getClass
      val constructor = classType.getDeclaredConstructors.head
      val argumentCount = constructor.getParameterTypes.length
      val fields = classType.getDeclaredFields
      val arguments = (0 until argumentCount) map { i =>
        val fieldName = fields(i).getName
        val fieldValue = withObj.getClass.getMethod(fieldName).invoke(withObj)
        fieldValue match {
          case None => classType.getMethod(fieldName).invoke(merge)
          case _ => fieldValue
        }
      }
      Success(constructor.newInstance(arguments: _*).asInstanceOf[Product])
    } catch {
      case e: Exception => Failure(e)
    }
  }

}
