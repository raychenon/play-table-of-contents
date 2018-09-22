import akka.actor.ActorSystem
import blockchain.data.BlockReader
import blockchain.services.BlockchainExplorerService
import com.typesafe.config.ConfigFactory
import context.MyExecutionContext
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext, Configuration}

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

  lazy val tableOfContentController = new _root_.controllers.TableOfContentController(ec,controllerComponents)

  lazy val config = new Configuration(ConfigFactory.load())
  lazy val blockReader = new BlockReader(config)
  lazy val service = new BlockchainExplorerService(blockReader)
  lazy val blockchainController = new _root_.blockchain.controllers.BlockchainController(ec,controllerComponents,service,blockReader)

  lazy val router: Router = new _root_.router.Routes(httpErrorHandler, tableOfContentController,blockchainController, assets)
}
