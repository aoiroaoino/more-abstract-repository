package example.domain.lifecycle.user.simple

import cats.data.Kleisli
import example.domain.lifecycle.user.{DBSession, UserRepository}
import example.domain.model.user.{User, UserId}

import scala.concurrent.{ExecutionContext, Future}

class UserRepositoryOnJDBCImplByFuture(implicit ec: ExecutionContext) extends UserRepository[Kleisli[Future, DBSession, ?]] {

  override def store(user: User): Kleisli[Future, DBSession, DBSession] = ???

  override def resolveBy(userId: UserId): Kleisli[Future, DBSession, Option[User]] = ???

  override def resolveByName(name: String): Kleisli[Future, DBSession, Option[User]] = ???
}
