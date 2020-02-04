package models

import java.util.{Date, UUID}

import scala.xml.Elem


/**
  * CREATE TABLE account(
  * account_id UUID,
  * balance Double,
  * user_id UUID,
  * creation_date timestamp
  * Primary Key(user_id, account_id)
  * )
  */

sealed trait Type

case object Savings extends Type {
  override def toString = "savings"
}

case object Current extends Type {
  override def toString: String = "current"
}

object AccountFactory {
  def getAccount(_type: String): Type = {
    _type.toLowerCase match {
      case "savings" => Savings
      case "current" => Current
    }
  }
}

case class AccountDTO(balance: Double, _type: Type, userId: String)

case class Account(id: UUID, balance: Double, creationDate: Date, _type: Type) {
  def toXML(): Elem = <account>
    <id>{id}</id>
    <balance>
      {balance}
    </balance>
    <creationDate>
      {creationDate}
    </creationDate>
    <type>
      {_type.toString}
    </type>
  </account>
}
