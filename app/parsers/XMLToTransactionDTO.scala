package parsers

import javax.inject.{Inject, Singleton}
import models.TransactionDTO
import play.api.mvc.Headers

import scala.xml.NodeSeq

@Singleton
class XMLToTransactionDTO{

  def convert(body: NodeSeq, headers: Headers): Option[TransactionDTO] = {
    val xmlParser = new XMLAttributeParser(body)
    for {
      amount <- xmlParser.get("amount")
      recipientId <- xmlParser.get("recipientId")
      senderId <- xmlParser.get("senderId")
    } yield {
      TransactionDTO(amount.toDouble, senderId, recipientId)
    }
  }
}
