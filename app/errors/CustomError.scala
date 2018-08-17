package errors

/**
  * A custom error class
  * @param status http status
  * @param code unique error code
  * @param message a formal message to display
  * @param detailedMessage a more detailed description for the error
  */
case class CustomError(status : Int,
                       code : Int,
                       message : String,
                       detailedMessage : Option[String] = None) extends Throwable
