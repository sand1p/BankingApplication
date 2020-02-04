package controller

import akka.actor.{ActorSystem, Props}
import concurrent.{CreateRequestsMsg, RequestClientActor}
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.mvc.{AbstractController, ControllerComponents}
import services.{AccountService, TransactionService}

@Singleton
class ConcurrencyController @Inject() (cc: ControllerComponents,
                                       system: ActorSystem,
                                       accountService: AccountService,
                                       transactionService: TransactionService) extends AbstractController(cc){

  def generate(concurrency: Int) = Action{ request =>
    val userId = request.headers.get("user_id")
    val actorSystem = ActorSystem("BankingApp")
    val requestGenerator = actorSystem.actorOf(Props(classOf[RequestClientActor],accountService, transactionService))
    Logger.info("")
    requestGenerator ! CreateRequestsMsg(concurrency)
    Ok(<response><status>Success</status><message>Request Generation has started</message></response>)
  }

}
