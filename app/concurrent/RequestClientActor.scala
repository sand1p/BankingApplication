package concurrent

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

import akka.actor.{Actor, Props}
import javax.inject.Inject
import models.TransactionDTO
import play.api.Logger
import services.{AccountService, TransactionService}

case class CreateRequestsMsg(accounts: Int)

case class StartTransactionCreationMsg(senderId: String, recipientId: String)

class RequestClientActor @Inject()(accountService: AccountService,
                                   transactionService: TransactionService) extends Actor {
  val dummyAccountGenerator = context.actorOf(Props(classOf[DummyAccountGenerator], accountService))
  val transactionCreator = context.actorOf(Props(classOf[TransactionCreatorActor], transactionService))

  override def receive: Receive = {
    case CreateRequestsMsg(accounts) => {

     Logger.info(s"Recieved Create account Request: for $accounts accounts")
      for (i <- 1 to accounts) {
        val senderId = UUID.randomUUID()
        val recipientId = UUID.randomUUID()
        println(s"Creating Account for sender : $senderId and Recipient: $recipientId")
        dummyAccountGenerator ! CreateAccountMsg(senderId.toString, recipientId.toString)
      }
    }

    case StartTransactionCreationMsg(senderId, recipientId) => {
      def getRandomBalance: Double = {
        ThreadLocalRandom.current().nextDouble()
      }

      val transaction = TransactionDTO(getRandomBalance, senderId, recipientId)
      transactionCreator ! CreateTransactionMsg(transaction)
    }

    case _ => Logger.info("Error: Unknown Message")
  }
}