package blockchain.json

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class Transation1(
  sender: String,
  recipient: String,
  amount: Int,
  fees: Int
)


object Transation1{

  val transation1Reads: Reads[Transation1] = (
      (JsPath \ "sender").read[String] and
      (JsPath \ "recipient").read[String] and
      (JsPath \ "amount").read[Int] and
      (JsPath \ "amount").read[Int]
    )(Transation1.apply _)

}