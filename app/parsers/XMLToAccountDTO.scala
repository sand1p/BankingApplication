package parsers

import javax.inject.Singleton
import models.{AccountDTO, AccountFactory}
import play.api.mvc.Headers

import scala.xml.NodeSeq

@Singleton
class XMLToAccountDTO {

  /**
    * user_id uuid,
    * id uuid,
    * balance double,
    * creation_date timestamp,
    * type text,
    *
    * @param body
    * @param headers
    * @return
    */
  def convert(body: NodeSeq, headers: Headers): Option[AccountDTO] = {
    val userId = headers.get("user_id")
    val xmlParser = new XMLAttributeParser(body)
    for {
      userId <- userId
      balance <- xmlParser.get("balance")
      _type <- xmlParser.get("type")
    } yield {
      AccountDTO(balance.toDouble, AccountFactory.getAccount(_type), userId)
    }
  }
}
