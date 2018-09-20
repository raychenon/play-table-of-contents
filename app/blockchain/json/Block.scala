package blockchain.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Block(
  date: Long,
  count: Int,
  transactions: Seq[Transaction1] )
{
  

}

object Block{

  implicit val blockReads: Reads[Block] = (
    (JsPath \ "date").read[Long] and
    (JsPath \ "count").read[Int] and
    (JsPath \ "txs").read[Seq[Transaction1]]
    )(Block.apply _)
  

}

case class Transaction1(
                         sender: String,
                         recipient: String,
                         amount: Int,
                         fees: Int
                       )

object Transaction1{

  implicit val fmt = Json.format[Transaction1]

  implicit val transation1Reads: Reads[Transaction1] = (
        (JsPath \ "sender").read[String] and
        (JsPath \ "recipient").read[String] and
        (JsPath \ "amount").read[Int] and
        (JsPath \ "fees").read[Int]
      )(Transaction1.apply _)

}

