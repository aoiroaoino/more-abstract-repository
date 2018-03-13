package example.domain.lifecycle.user
package free

import example.domain.model.user.{User, UserId}

object UserRepositoryOnJDBC extends UserRepository[UserRepositoryFree] {

  override def store(user: User): UserRepositoryFree[Unit] =
    UserRepositoryOp.store(user)

  override def resolveBy(userId: UserId): UserRepositoryFree[Option[User]] =
    UserRepositoryOp.resolveBy(userId)

  override def resolveByName(name: String): UserRepositoryFree[Option[User]] =
    UserRepositoryOp.resolveByName(name)
}

