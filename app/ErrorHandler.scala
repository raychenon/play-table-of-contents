import javax.inject.{Inject, Provider, Singleton}

import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router

import scala.concurrent._

/**
  * Provides a stripped down error handler that does not use HTML in error pages, and
  * prints out debugging output.
  *
  * https://www.playframework.com/documentation/2.6.x/ScalaErrorHandling
  */
@Singleton
class ErrorHandler @Inject() (
   env: Environment,
   config: Configuration,
   sourceMapper: OptionalSourceMapper,
   router: Provider[Router]
  ) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override def onProdServerError(request: RequestHeader, exception: UsefulException) = {
    Future.successful(
      InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }

  override def onForbidden(request: RequestHeader, message: String) = {
    Future.successful(
      Forbidden("You're not allowed to access this resource.")
    )
  }
}