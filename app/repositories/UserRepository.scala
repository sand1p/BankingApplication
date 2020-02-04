package repositories

import connections.CassandraClient
import javax.inject.{Inject, Singleton}
import models.UserDTO

import scala.concurrent.Future

@Singleton
class UserRepository @Inject()(cassandraClient: CassandraClient) {
  private val keyspace = "banking_system"
  private val userTable = "user"
  private val session = cassandraClient.getSession(keyspace)

  private val createUser = session.prepare(s"INSERT INTO $userTable (id , email_id , mobile_number , name , password ) VALUES (now(),?,?,?,?)")

  def create(userDTO: UserDTO): Future[_] = {
    import userDTO._
    session.execute(createUser.bind(emailId, mobileNumber.asInstanceOf[Object], name, password))
    Future.successful(())
  }

}