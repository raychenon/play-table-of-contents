import javax.inject.Inject

import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}

class TOCApplicationLoader extends ApplicationLoader{

  private var components: TOCComponents = _

  def load(context: ApplicationLoader.Context): Application = {
    components = new TOCComponents(context)
    components.application
  }
}

class TOCComponents(context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
  with _root_.controllers.AssetsComponents{

  lazy val tableOfContentController = new _root_.controllers.TableOfContentController(controllerComponents)

  lazy val router: Router = new _root_.router.Routes(httpErrorHandler, tableOfContentController, assets)
}

class MyExecutionContext(actorSystem: ActorSystem)
  extends CustomExecutionContext(actorSystem, "my.dispatcher.name")