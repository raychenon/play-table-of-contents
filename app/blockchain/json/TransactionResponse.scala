package blockchain.json

import play.api.libs.json.Json


case class TransactionResponse(
  sender_address: String,
  recipient_address: String,
  amount: Int,
  fees: Int,
  block_time: Long
)

object TransactionResponse{
  implicit val transactionResponseFormat = Json.format[TransactionResponse]
}