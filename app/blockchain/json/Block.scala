package blockchain.json

import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.libs.functional.syntax._

case class Block(
  date: Long,
  count: Int,
  transactions: Seq[Transaction1]
)

object Block{

  val blockReads: Reads[Block] = (
    (JsPath \ "date").read[Long] and
    (JsPath \ "count").read[Int] and
    (JsPath \ "txs").read[Seq[Transaction1]]
    )(Block.apply _)
  

}
