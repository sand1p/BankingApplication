package controller

import javax.inject.{Inject, Singleton}
import parsers.XMLToTransactionDTO
import play.api.Logger
import play.api.mvc.{AbstractController, ControllerComponents}
import services.TransactionService

@Singleton
class TransactionController @Inject()(cc: ControllerComponents,
                                      xmlToTransactionDTO: XMLToTransactionDTO,
                                      transactionService: TransactionService) extends AbstractController(cc) {

  def create = Action(parse.xml) { request =>

    xmlToTransactionDTO.convert(request.body, request.headers) match {
      case Some(transaction) =>
        Logger.info(s"Transactions: $transaction")
        transactionService.create(transaction) match {
          case Some(result) => if (result) Ok(<response>
            <status>Success</status> <message>Transaction Successful</message>
          </response>)
          else InternalServerError(<response>
            <message>Something went wrong</message> <status>Failure</status>
          </response>)
          case None => BadRequest(<response>
            <status>Failure</status> <message>Something went wrong</message>
          </response>)
        }
      case None => BadRequest(<response>
        <status>Failure</status> <message>Incomplete Details in Request</message>
      </response>)
    }
  }

  def getTransactions = Action { request =>
    request.headers.get("account_id") match {
      case Some(accountId) =>
        Ok(<transactions>
          {transactionService.get(accountId).map(transaction => transaction.asXML)}
        </transactions>)
      case None => BadRequest(<response><status>Failure</status><message></message></response>)
    }
  }
}
