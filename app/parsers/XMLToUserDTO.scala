package parsers

import javax.inject.Singleton
import models.UserDTO
import play.api.mvc.Headers

import scala.xml.NodeSeq

@Singleton
class XMLToUserDTO {

  def convert(body: NodeSeq, headers: Headers): Option[UserDTO] = {
    val xmlParser = new XMLAttributeParser(body)
    for {
      emailId <- xmlParser.get("emailId")
      mobileNumber <- xmlParser.get("mobileNumber")
      password <- xmlParser.get("password")
      name <- xmlParser.get("name")
    } yield {
      UserDTO(emailId, mobileNumber.toLong, password, name)
    }
  }
}
