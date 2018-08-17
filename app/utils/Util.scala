package utils

import org.joda.time.{DateTime, DateTimeZone}

/**
  * A common utilities
  */
object Util {
  val NOW: DateTime = new DateTime(DateTimeZone.UTC)
}

/**
  * A random uuid generator
  */
object RandomUUID {
  def generate: String = java.util.UUID.randomUUID().toString
}
