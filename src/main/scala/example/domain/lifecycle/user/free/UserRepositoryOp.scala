package example.domain.lifecycle.user.free

import cats.free.Free
import example.domain.model.user.{User, UserId}

sealed trait UserRepositoryOp[A]

object UserRepositoryOp {

  case class Store(user: User) extends UserRepositoryOp[Unit]

  case class ResolveBy(userId: UserId) extends UserRepositoryOp[Option[User]]

  case class ResolveByName(name: String) extends UserRepositoryOp[Option[User]]

  def store(user: User): UserRepositoryFree[Unit] =
    Free.liftF[UserRepositoryOp, Unit](Store(user))

  def resolveBy(userId: UserId): UserRepositoryFree[Option[User]] =
    Free.liftF[UserRepositoryOp, Option[User]](ResolveBy(userId))

  def resolveByName(name: String): UserRepositoryFree[Option[User]] =
    Free.liftF[UserRepositoryOp, Option[User]](ResolveByName(name))
}
