package models

import java.util.Date

import scala.xml.NodeSeq


case class Transaction(id: String, senderId: String, recipientId: String, date: Date, amount: Double) {
  def asXML: NodeSeq = {
    <transaction>
    <id>{id}</id>
      <senderId>{senderId}</senderId>
      <recipientId>{recipientId}</recipientId>
      <date>{date}</date>
      <amount>{amount}</amount>
    </transaction>
  }

}

case class TransactionDTO(amount: Double, senderId: String, recipientId: String) {
  override def toString: String = s"Sender Id: $senderId, reciever Id: $recipientId, amount:  $amount"
}
