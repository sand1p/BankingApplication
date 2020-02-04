package models

import scala.xml.NodeSeq

case class UserDTO(emailId: String, mobileNumber: Long, password: String, name: String)

case class User(id: String, emailId: String, mobileNumber: String, password: String, name: String) {

  def toUser: NodeSeq = {
    <user>
      <id>
        {id}
      </id>
      <emailId>
        {emailId}
      </emailId>
      <mobileNumber>
        {mobileNumber}
      </mobileNumber>
      <password>
        {password}
      </password>
      <name>
        {name}
      </name>
    </user>
  }
}