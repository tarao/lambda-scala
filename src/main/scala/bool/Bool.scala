package bool

import scala.language.higherKinds

sealed trait Bool {
  type ?[T, Then <: T, Else <: T] <: T
  type ||[b <: Bool] <: Bool
  type &&[b <: Bool] <: Bool
  type ! <: Bool
}

sealed trait True extends Bool {
  type ?[T, Then <: T, Else <: T] = Then
  type ||[b <: Bool] = True
  type &&[b <: Bool] = b
  type ! = False
}

sealed trait False extends Bool {
  type ?[T, Then <: T, Else <: T] = Else
  type ||[b <: Bool] = b
  type &&[b <: Bool] = False
  type ! = True
}
