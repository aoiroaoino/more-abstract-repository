package example.domain.lifecycle.user

import cats.data.Kleisli
import example.domain.model.user.{User, UserId}
import example.domain.lifecycle.user.free.UserRepositoryFree

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait UserRepository[F[_]] {

  def store(user: User): F[Unit]

  def resolveBy(userId: UserId): F[Option[User]]

  def resolveByName(name: String): F[Option[User]]
}

object UserRepository {

  // 実装ごとにインスタンスを定義していく必要がある
  // 関数で頑張る例
  type Func[A] = DBSession => Try[A]
  lazy val simpleImplByTryFunc: UserRepository[Func] =
    new simple.UserRepositoryOnJDBCImplByTryFunc()

  // Kleisli を用いた例
  lazy val blocking: UserRepository[Kleisli[Try, DBSession, ?]] =
    new simple.UserRepositoryOnJDBCImplByTry

  def nonBlocking(implicit ec: ExecutionContext): UserRepository[Kleisli[Future, DBSession, ?]] =
    new simple.UserRepositoryOnJDBCImplByFuture

  // UserRepositoryOnJDBC 内の暗黙の値を import してスコープに入れることで実装を切り替えられるので不要
  // def typeclassImpl

  // DSL の構築のみで、実行時に Future, Try などの具象型が決まるので、これだけあればいい。
  lazy val freemonadImpl: UserRepository[UserRepositoryFree] =
    free.UserRepositoryOnJDBC
}