package controller

import javax.inject.{Inject, Singleton}
import parsers.XMLToUserDTO
import play.api.mvc.{AbstractController, ControllerComponents}
import services.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserController @Inject()(cc: ControllerComponents,
                               xmlToUserDTO: XMLToUserDTO,
                               userService: UserService) extends AbstractController(cc) {

  def create = Action.async(parse.xml) { request =>
    val user = xmlToUserDTO.convert(request.body, request.headers)
    user match {
      case Some(userDTO) => userService.create(userDTO).map(_ => Ok(<response>
        <status>Success</status> <nessage>User created successfully</nessage>
      </response>))
      case None =>
        Future(BadRequest(<response>
          <status>Failure</status> <message>Complete details in request</message>
        </response>))
    }
  }

}
