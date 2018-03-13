package example

import cats.data.Kleisli
import example.application.service.{AuthenticationService, AuthenticationServiceForTypeclass}
import example.domain.lifecycle.user.{DBSession, UserRepository}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try


object Hello extends Greeting with App {
  println(greeting)

  import cats.instances.all._

  val simple = {

    type BlockingJDBC[A] = Kleisli[Try, DBSession, A]
    type NonBlockingJDBC[A] = Kleisli[Future, DBSession, A]

    val session: DBSession = ()
    implicit val ec: ExecutionContext = ExecutionContext.global

    val serviceTry = new AuthenticationService[BlockingJDBC](UserRepository.blocking)
    serviceTry.authenticate("John Doe", "password").run(session) // Try(Some(User(...)))

    val serviceFuture = new AuthenticationService[NonBlockingJDBC](UserRepository.nonBlocking)
    serviceFuture.authenticate("John Doe", "password").run(session) // Future(Some(User(...)))
  }

  val typeclass = {
    import example.domain.lifecycle.user.typeclass.UserRepositoryOnJDBC._

    val session: DBSession = ()
    implicit val ec: ExecutionContext = ExecutionContext.global

    val serviceTry = new AuthenticationServiceForTypeclass[BlockingJDBC]
    serviceTry.authenticate("John Doe", "password").run(session) // Try(Some(User(...)))

    val serviceFuture = new AuthenticationServiceForTypeclass[NonBlockingJDBC]
    serviceFuture.authenticate("John Doe", "password").run(session) // Future(Some(User(...)))
  }

  val free = {
    import example.domain.lifecycle.user.free._

    val service: AuthenticationService[UserRepositoryFree] =
      new AuthenticationService(UserRepositoryOnJDBC)

    val session: DBSession = ()
    implicit val ec: ExecutionContext = ExecutionContext.global

    val authProg = service.authenticate("John Doe", "password")

    authProg.foldMap(UserRepositoryOnJDBCInterp.tryInterp(session)) // Try(Some(User(...)))
    authProg.foldMap(UserRepositoryOnJDBCInterp.futureInterp(session)) // Future(Some(User(...)))
  }
}

trait Greeting {
  lazy val greeting: String = "hello"
}
