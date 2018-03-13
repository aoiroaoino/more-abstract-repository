package example.domain.lifecycle.user
package simple

import example.domain.model.user.{User, UserId}

import scala.util.Try

object UserRepositoryOnJDBCImplByTryFunc {
  type BlockingJDBC[A] = DBSession => Try[A]
}

import UserRepositoryOnJDBCImplByTryFunc._

class UserRepositoryOnJDBCImplByTryFunc extends UserRepository[BlockingJDBC] {

  override def store(user: User): DBSession => Try[Unit] = ???

  override def resolveBy(userId: UserId): DBSession => Try[Option[User]] = ???

  override def resolveByName(name: String): DBSession => Try[Option[User]] = ???
}
