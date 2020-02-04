package services

import javax.inject.{Inject, Singleton}
import models.{Account, AccountDTO}
import repositories.AccountRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AccountService@Inject() ( accountRepository: AccountRepository) {
  def getBalance(id: String) = {
    accountRepository.getBalance(id)
  }

  def create(account: AccountDTO) = {
    accountRepository.create(account)
  }

  def createDummy(account: Account): Future[Boolean] = {
    accountRepository.createDummy(account)
  }
}
