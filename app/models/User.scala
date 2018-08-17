package models

import org.joda.time.DateTime

case class User(id: Option[String],
                biodata: Option[BioData],
                careerProfile: Option[CareerProfile],
                creationDateTime: Option[DateTime],
                modifiedDateTime: Option[DateTime])




