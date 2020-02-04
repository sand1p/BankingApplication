package services

import javax.inject.{Inject, Singleton}
import models.UserDTO
import repositories.UserRepository

import scala.concurrent.Future
@Singleton
class UserService@Inject() (userRepository: UserRepository) {

  def create(userDTO: UserDTO): Future[_] = {
    userRepository.create(userDTO)
  }

}
