import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import context.MyExecutionContext
import controllers.TableOfContentController
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import com.softwaremill.macwire._

class TOCApplicationLoader extends ApplicationLoader{

  private var components: TOCComponents = _

  def load(context: ApplicationLoader.Context): Application = {
    val exeContext =  new MyExecutionContext(ActorSystem("tocActor", ConfigFactory.load()))
    components = new TOCComponents(exeContext,context)
    components.application
  }
}

class TOCComponents(ec: MyExecutionContext, context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
  with _root_.controllers.AssetsComponents{

  lazy val tableOfContentController = wire[TableOfContentController]

  lazy val router: Router = new _root_.router.Routes(httpErrorHandler, tableOfContentController, assets)
}
