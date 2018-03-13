package example.application.service

import cats.Monad
import cats.syntax.functor._
import example.domain.lifecycle.user.UserRepository
import example.domain.model.user.{User, UserId}

class AuthenticationService[F[_]: Monad](userRepository: UserRepository[F]) {
  // 認証に成功すると true
  def authenticate(name: String, password: String): F[Boolean] =
    userRepository
      .resolveByName(name)
      .map(_.exists(_.password == password))
}

// UserRepository を型クラスとみなした場合の実装
class AuthenticationServiceForTypeclass[F[_]: Monad: UserRepository] {

  def authenticate(name: String, password: String): F[Boolean] =
    implicitly[UserRepository[F]]
      .resolveByName(name)
      .map(_.exists(_.password == password))
}