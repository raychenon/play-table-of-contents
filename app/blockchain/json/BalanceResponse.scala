package blockchain.json

import play.api.libs.json.Json


case class BalanceResponse(balance: Int)

object BalanceResponse{

  implicit val balanceResponseFormat = Json.format[BalanceResponse]

}