package helper

import org.scalatest.{FunSpec, Matchers, OptionValues, Inside, Inspectors}

abstract class UnitSpec extends FunSpec
    with Matchers with OptionValues with Inside with Inspectors {
  case class Equals[A >: B <: B, B]()
}
