package helpers

import errors.CustomError
import models._
import play.api.libs.json._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

/**
  * A wrapper that holds format definitions for all models
  */
trait FormatWrapper {

  implicit val implCustomErrorFormat: OFormat[CustomError] = Json.format[CustomError]
  implicit val implBioDataFormat: OFormat[BioData] = Json.format[BioData]
  implicit val implTotalDurationFormat: OFormat[TotalDuration] = Json.format[TotalDuration]
  implicit val implCareerProfileFormat: OFormat[CareerProfile] = Json.format[CareerProfile]
  implicit val implUserFormat: OFormat[User] = Json.format[User]

}
