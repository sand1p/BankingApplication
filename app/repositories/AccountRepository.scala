package repositories

import java.util.UUID

import connections.CassandraClient
import javax.inject.{Inject, Singleton}
import models.{Account, AccountDTO}
import play.api.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AccountRepository @Inject()(cassandraClient: CassandraClient) {

  private val keyspace = "banking_system"
  private val table = "account"

  private val session = cassandraClient.getSession(keyspace)

  private val insertStatement = session.prepare(s"INSERT INTO $table (id , balance , creation_date , type ) VALUES ( now(), ?, toTimestamp(now()), ?)")
  private val selectBalance = session.prepare(s"SELECT balance FROM $table WHERE id = ?")
  private val updateBalance = session.prepare(s"UPDATE $table SET balance = ? WHERE id = ? IF balance = ?")

  def create(account: AccountDTO): Unit = {
    import account._
    session.execute(insertStatement.bind(balance.asInstanceOf[Object], _type.toString))
    Unit
  }

  val insertDummy = session.prepare(s"INSERT INTO $table (id, balance, creation_date, type) VALUES ( ?, ?, toTimestamp(now()), ?)")

  def createDummy(account: Account): Future[Boolean] = {
    Future {
      import account._
      Logger.info(s"Insert query to create account for: $account")
      session.execute(insertDummy.bind(id, balance.asInstanceOf[Object], _type.toString)).wasApplied()
    }
  }

  def getBalance(accoutId: String): Option[Double] = {
    import scala.collection.JavaConverters._
    session.execute(selectBalance.bind(UUID.fromString(accoutId))).asScala.headOption.map(balance => balance.getDouble("balance"))
  }

  def update(id: String, updatedBalance: Double, currentBalance: Double): Boolean = {
    Logger.info(s"Update Query : Account Id: $id, previous Balance: $currentBalance,  updated Balance: $updatedBalance")
    val statement = updateBalance.bind(updatedBalance.asInstanceOf[Object], UUID.fromString(id), currentBalance.asInstanceOf[Object])
    Logger.info(s"Statement: ${statement.toString}")
    val result = session.execute(statement).wasApplied()
    Logger.info(s"result : $result")
    result
  }

}