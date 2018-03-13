package example.domain.lifecycle.user
package typeclass

import cats.data.Kleisli
import example.domain.model.user.{User, UserId}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

// UserRepository の定義を Tagless Final の DSL 定義とみなし、実装を提供する
object UserRepositoryOnJDBC {

  type BlockingJDBC[A] = Kleisli[Try, DBSession, A]
  type NonBlockingJDBC[A] = Kleisli[Future, DBSession, A]

  implicit def userRepositoryTry: UserRepository[BlockingJDBC] =
    new UserRepository[BlockingJDBC] {
      override def store(user: User): BlockingJDBC[DBSession] = ???
      override def resolveBy(userId: UserId): BlockingJDBC[Option[User]] = ???
      override def resolveByName(name: String): BlockingJDBC[Option[User]] = ???
    }

  implicit def userRepositoryFuture(implicit ec: ExecutionContext): UserRepository[NonBlockingJDBC] =
    new UserRepository[NonBlockingJDBC] {
      override def store(user: User): NonBlockingJDBC[DBSession] = ???
      override def resolveBy(userId: UserId): NonBlockingJDBC[Option[User]] = ???
      override def resolveByName(name: String): NonBlockingJDBC[Option[User]] = ???
    }
}
