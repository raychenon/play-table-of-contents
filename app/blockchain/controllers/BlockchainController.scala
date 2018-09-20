package blockchain.controllers


import blockchain.data.BlockReader
import javax.inject.Inject
import context.MyExecutionContext
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class BlockchainController @Inject()(ec: MyExecutionContext,cc: ControllerComponents) extends AbstractController(cc){


  val reader = new BlockReader()

  def getBalanceFrom(address: String) = Action {
    NotFound(s"Not found for $address")
  }

  def getType1() = Action {

    Ok(reader.readTransactions1().toString)
//    Ok(Json.prettyPrint(Json.toJson(reader.readTransactions1())))
  }

  def getType2() = Action {
    Ok(reader.readType2())
  }
  

}