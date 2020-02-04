package repositories

import java.util.{Date, UUID}

import com.datastax.driver.core.Row
import connections.CassandraClient
import javax.inject.{Inject, Singleton}
import models.{Transaction, TransactionDTO}
import play.api.Logger

@Singleton
class TransactionRepository @Inject()(cassandraClient: CassandraClient, accountRepository: AccountRepository) {
  private val keyspace = "banking_system"
  private val table = "transaction"
  private val session = cassandraClient.getSession(keyspace)
  val insertTransaction = session.prepare(" INSERT INTO transaction (id, date, sender_id, recipient_id, amount) VALUES (now(),toTimestamp(now()), ?, ? ,? )")

  def create(transaction: TransactionDTO): Option[Boolean] = {
    import transaction._
    for {
      senderBalance <- accountRepository.getBalance(senderId)
      recipientBalance <- accountRepository.getBalance(recipientId)
      if isValidMinimumBalance(senderBalance, amount)
    } yield {
      Logger.info(s"Transaction details: ${transaction.toString}")
      Logger.info(s"Sender balance: $senderBalance, reciever balance: $recipientBalance")
      if(accountRepository.update(senderId, getUpdatedBalance(senderBalance, amount)((x, y) => x - y), senderBalance)){
        Logger.info("Updated sender account ")
        if(accountRepository.update(recipientId, getUpdatedBalance(recipientBalance, amount)((x, y) => x + y), recipientBalance)){
          Logger.info("Updated recipients account ")
          val result = session.execute(insertTransaction.bind(UUID.fromString(senderId), UUID.fromString(recipientId), amount.asInstanceOf[Object])).wasApplied()
          Logger.info(s"Transaction applied : $result")
          result
        } else false
      } else {
        false
      }
    }
  }

  private val selectTransactions = session.prepare(s"select * from  $table")

  /**
    *
  case class Transaction(id: String, senderId: String, recipientId: String, date: Date, amount: Double)
    * @param row
    * @return
    */
  def toTransaction(row: Row): Transaction = {
    Transaction(
      row.getString("id"),
      row.getString("senderId"),
      row.getString("recipientId"),
      row.getDate("date").asInstanceOf[Date],
      row.getDouble("amount")
    )
  }

  def getAll(): List[Transaction] = {
    import scala.collection.JavaConverters._
    session.execute(selectTransactions.bind()).asScala.map(toTransaction).toList
  }

  private def getUpdatedBalance(balance: Double, amount: Double)(f: (Double, Double) => Double) = f(balance, amount)

  private def isValidMinimumBalance(balance: Double, amount: Double): Boolean = balance - amount > 0

}
