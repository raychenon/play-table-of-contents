import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import context.MyExecutionContext
import controllers.TableOfContentController
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import com.softwaremill.macwire._
import router.Routes

class TOCApplicationLoader extends ApplicationLoader{

  def load(context: ApplicationLoader.Context): Application = {
    val exeContext = new MyExecutionContext(ActorSystem("tocActor", ConfigFactory.load()))
    new TOCComponents(exeContext,context).application
  }
}

class TOCComponents(ec: MyExecutionContext, context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
  with _root_.controllers.AssetsComponents{

  lazy val tableOfContentController = wire[TableOfContentController]

  lazy val router: Router = wire[Routes]
}
