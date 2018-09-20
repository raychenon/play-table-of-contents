package blockchain.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class BlockchainType1(
  date: Long,
  count: Int,
  transactions: Seq[Transaction1] )
{


}

object BlockchainType1{

  implicit val block1Reads: Reads[BlockchainType1] = (
    (JsPath \ "date").read[Long] and
    (JsPath \ "count").read[Int] and
    (JsPath \ "txs").read[Seq[Transaction1]]
    )(BlockchainType1.apply _)
  

}
