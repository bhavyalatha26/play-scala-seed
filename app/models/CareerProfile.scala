package models

case class TotalDuration(years : Int,months : Int , days : Int)

case class CareerProfile(currentDesignation : Option[String],
                         currentOrganization : Option[String],
                         totalExperience : Option[TotalDuration],
                         careerBreak : Option[TotalDuration])
