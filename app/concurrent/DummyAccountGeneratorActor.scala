package concurrent

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

import akka.actor.Actor
import javax.inject.{Inject, Singleton}
import models.{Account, AccountFactory, Type}
import play.api.Logger
import services.AccountService

import scala.concurrent.ExecutionContext.Implicits.global
case class CreateAccountMsg(senderId: String, recipient: String)

@Singleton
class DummyAccountGenerator @Inject()(accountService: AccountService) extends Actor {

  override def receive: Receive = {
    case CreateAccountMsg(senderId, recipientId) =>
      Logger.info(s"Create Account request for sender: $senderId, recipient $recipientId")
      def getRandomBalance: Double = {
        ThreadLocalRandom.current().nextDouble()*1000
      }
      def getType: Type = {
        val balance = Math.random() * 10
        AccountFactory.getAccount(if (balance < 5) "current" else "savings")
      }

      val senderAccount = Account(UUID.fromString(senderId), getRandomBalance, null, getType)
      val recipientAccount = Account(UUID.fromString(recipientId), getRandomBalance, null, getType)
      for {
        senderResult <- accountService.createDummy(senderAccount)
        recipientResult <- accountService.createDummy(recipientAccount)
        if senderResult && recipientResult
      } yield {
        Logger.info(s"Created Account for $senderAccount")
        Logger.info(s"Created Account for $recipientAccount")
        sender ! StartTransactionCreationMsg(senderId, recipientId)
      }

    case _ => Logger.info("Error: received unknown message")
  }
}

