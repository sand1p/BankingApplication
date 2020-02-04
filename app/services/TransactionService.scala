package services

import javax.inject.{Inject, Singleton}
import models.{Transaction, TransactionDTO}
import repositories.TransactionRepository

@Singleton
class TransactionService @Inject()(transactionRepository: TransactionRepository) {
  def get(accoutId: String): List[Transaction] = {
    transactionRepository.getAll()
  }

  def create(transaction: TransactionDTO): Option[Boolean] = {
    transactionRepository.create(transaction)
  }
}
