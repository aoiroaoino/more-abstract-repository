package example.domain.lifecycle.user

import cats.free.Free

package object free {

  type UserRepositoryFree[A] = Free[UserRepositoryOp, A]

}
