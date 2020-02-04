package controller

import javax.inject.{Inject, Singleton}
import models.AccountDTO
import parsers.XMLToAccountDTO
import play.api.mvc.{AbstractController, ControllerComponents}
import services.AccountService

@Singleton
class AccountController @Inject()(cc: ControllerComponents, accountService: AccountService, xmlToAccountDTO: XMLToAccountDTO) extends AbstractController(cc) {

  def create = Action(parse.xml) { request =>
    val account: Option[AccountDTO] = xmlToAccountDTO.convert(request.body, request.headers)
    account match {
      case Some(userAccount) => accountService.create(userAccount)
        Ok(<response>
          <message>Success</message>
        </response>)
      case None => BadRequest(<response>
        <message>Failure</message>
      </response>)
    }
  }

  def getBalance = Action {request =>
    request.headers.get("account_id") match {
      case Some(id) =>
        accountService.getBalance(id) match{
          case Some(balance) => Ok(<response><status>Success</status><account><id>{id}</id><balance>{balance}</balance></account></response>)
          case None => NotFound(<response><status>Failure</status><message>Account doesn't exists.</message></response>)
        }
      case None => BadRequest(<response><status>Failure</status><message>Incomplete input.</message></response>)
    }
  }

}
