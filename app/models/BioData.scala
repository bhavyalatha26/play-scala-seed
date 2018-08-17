package models

case class BioData(firstName: Option[String],
                   middleName: Option[String],
                   lastName: Option[String],
                   ageInYears : Option[Int],
                   gender : Option[String])
