package concurrent

import akka.actor.Actor
import javax.inject.Inject
import models.TransactionDTO
import play.api.Logger
import services.TransactionService

case class CreateTransactionMsg(transaction: TransactionDTO)

class TransactionCreatorActor @Inject()(transactionService: TransactionService) extends Actor {

  override def receive: Receive = {
    case CreateTransactionMsg(transaction: TransactionDTO) =>
      Logger.info(s"Create Transaction Request: $transaction")
      transactionService.create(transaction)
    case _ => Logger.info("Error: Unknown Message received")
  }
}