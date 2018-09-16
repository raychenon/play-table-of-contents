package blockchain.controllers


import javax.inject.Inject
import context.MyExecutionContext
import play.api.mvc.{AbstractController, ControllerComponents}

class BlockchainController @Inject()(ec: MyExecutionContext, cc: ControllerComponents) extends AbstractController(cc){


  def getBalanceFrom(address: String) = Action {
    NotFound(s"Not found for $address")
  }

}
