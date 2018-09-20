package blockchain.json

import play.api.libs.json._
import play.api.libs.functional.syntax._
  
case class BlockchainType2(
  date: Long,
  count: Int,
  transactions: Seq[Transaction2]
)

object BlockchainType2{

  implicit val block1Reads: Reads[BlockchainType2] = (
    (JsPath \ "date").read[Long] and
      (JsPath \ "count").read[Int] and
      (JsPath \ "txs").read[Seq[Transaction2]]
    )(BlockchainType2.apply _)
  
}


case class Transaction2(
  sender: String,
  recipient: String,
  senderbalanceChange: Int,
  recipientBalanceChange: Int
)


object Transaction2{

  implicit val transation2Reads: Reads[Transaction2] = (
        (JsPath \ "sender").read[String] and
        (JsPath \ "recipient").read[String] and
        (JsPath \ "senderBalanceChange").read[Int] and
        (JsPath \ "recipientBalanceChange").read[Int]
      )(Transaction2.apply _)

}
