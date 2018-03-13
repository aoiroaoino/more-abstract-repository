package example.domain.lifecycle.user.free

import cats.~>
import example.domain.lifecycle.user.DBSession
import example.domain.model.user.{User, UserId}

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

object UserRepositoryOnJDBCInterp {
  import UserRepositoryOp._

  private val db = mutable.Map.empty[UserId, User]

  def tryInterp(session: DBSession): UserRepositoryOp ~> Try =
    new (UserRepositoryOp ~> Try) {
      def apply[A](op: UserRepositoryOp[A]): Try[A] =
        op match {
          case Store(user) =>
            // exec update query
            Try(db.update(user.id, user))
          case ResolveBy(userId) =>
            // exec select query
            Try(db.get(userId))
          case ResolveByName(name) =>
            Try(db.collectFirst { case (_, u@User(_, n, _)) if n == name => u })
        }

    }

  def futureInterp(session: DBSession)(implicit ec: ExecutionContext): UserRepositoryOp ~> Future  =
    new (UserRepositoryOp ~> Future) {
      def apply[A](op: UserRepositoryOp[A]): Future[A] =
        op match {
          case Store(user) =>
            Future(db.update(user.id, user))
          case ResolveBy(userId) =>
            Future(db.get(userId))
          case ResolveByName(name) =>
            Future(db.collectFirst { case (_, u@User(_, n, _)) if n == name => u })
        }
    }
}
