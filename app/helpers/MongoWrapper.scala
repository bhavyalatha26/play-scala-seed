package helpers

import javax.inject.{Inject, Singleton}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.DefaultDB

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * A wrapper that handles mongo database connectivity to run all queries
  * @param reactiveMongoApi the reactive mongo api for mongodb
  */
@Singleton
class MongoWrapper @Inject()
(val reactiveMongoApi: ReactiveMongoApi) {

  def run(query: DefaultDB => Future[Any]): Future[Any] = {
    reactiveMongoApi.database.flatMap(query)
  }

}

