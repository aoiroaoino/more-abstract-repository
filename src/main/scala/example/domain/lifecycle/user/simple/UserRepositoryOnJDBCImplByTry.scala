package example.domain.lifecycle.user
package simple

import cats.data.Kleisli
import example.domain.model.user.{User, UserId}

import scala.util.Try

class UserRepositoryOnJDBCImplByTry extends UserRepository[Kleisli[Try, DBSession, ?]] {

  override def store(user: User): Kleisli[Try, DBSession, DBSession] = ???

  override def resolveBy(userId: UserId): Kleisli[Try, DBSession, Option[User]] = ???

  override def resolveByName(name: String): Kleisli[Try, DBSession, Option[User]] = ???
}
