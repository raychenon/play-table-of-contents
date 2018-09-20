package blockchain.controllers


import blockchain.data.BlockReader
import blockchain.services.BlockchainExplorerService
import javax.inject.Inject
import context.MyExecutionContext
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class BlockchainController @Inject()
  (ec: MyExecutionContext,
   cc: ControllerComponents,
   service: BlockchainExplorerService)
  extends AbstractController(cc){


  val reader = new BlockReader()

  def getBalanceFrom(address: String) = Action {
    Ok(Json.toJson(service.calculateBalance(address)))
//    NotFound(s"Not found for $address")
  }

  def getTransactionsFrom(address: String) = Action {
    Ok(Json.toJson(service.collectTransactions(address)))
//    NotFound(s"Not found for $address")
  }


  def getType1() = Action {

    Ok(reader.parseTransactions1().toString)
//    Ok(Json.prettyPrint(Json.toJson(reader.readTransactions1())))
  }

  def getType2() = Action {
    Ok(reader.parseTransactions2().toString)
//    Ok(reader.readType2())
  }
  

}