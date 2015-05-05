package nat

import scala.language.higherKinds
import bool.{Bool, True, False}

sealed trait Nat {
  type isZero <: Bool
  type ==[m <: Nat] <: Bool
  type >[m <: Nat] <: Bool
  type ++ <: Nat
  type -- <: Nat
  type +[m <: Nat] <: Nat
}

sealed trait Z extends Nat {
  type isZero = True
  type ==[m <: Nat] = m#isZero
  type >[m <: Nat] = False
  type ++ = S[Z]
  type -- = Z
  type +[m <: Nat] = m
}

sealed trait S[n <: Nat] extends Nat {
  type isZero = False
  type ==[m <: Nat] = m#isZero# ! # && [n# == [m# --]]
  type >[m <: Nat] = m#isZero# || [ n# > [m# --] ]
  type ++ = S[S[n]]
  type -- = n
  type +[m <: Nat] = S[n# + [m]]
}
