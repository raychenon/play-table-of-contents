package blockchain.json

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class Transaction1(
  sender: String,
  recipient: String,
  amount: Int,
  fees: Int
)


object Transaction1{

  implicit val transaction1Format = Json.format[Transaction1]

//  val transation1Reads: Reads[Transaction1] = (
//      (JsPath \ "sender").read[String] and
//      (JsPath \ "recipient").read[String] and
//      (JsPath \ "amount").read[Int] and
//      (JsPath \ "fees").read[Int]
//    )(Transaction1.apply _)

}