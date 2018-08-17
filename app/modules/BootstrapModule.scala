package modules

import com.google.inject.AbstractModule
import daos._
import services._

/**
  * A module that serves as a bootstrap binder for all run time binding using guice
  */
class BootstrapModule extends AbstractModule {

  override def configure(): Unit = {
    /*
    DAO Bindings
     */
    bind(classOf[UserDAO]).to(classOf[UserDAOImpl])
    /*
    Service Bindings
     */
    bind(classOf[UserService]).to(classOf[UserServiceImpl])
  }

}
