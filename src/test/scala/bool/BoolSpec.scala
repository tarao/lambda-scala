package bool

trait X
trait A extends X
trait B extends X

class BoolSpec extends helper.UnitSpec {
  describe("True") {
    it("should always be true on disjuction") {
      Equals[True# || [True], True]
      Equals[True# || [False], True]
      Equals[True# || [True# || [True]], True]
      Equals[True# || [True# && [False]], True]
      Equals[True# || [True]# || [True], True]
      Equals[True# && [False]# || [True], True]
    }

    it("should be true iff the other is true on conjunction") {
      Equals[True# && [True], True]
      Equals[True# && [False], False]
      Equals[True# && [True# || [True]], True]
      Equals[True# && [True# && [False]], False]
      Equals[True# || [True]# && [True], True]
      Equals[True# && [False]# && [True], False]
    }

    it("should invert to false") {
      Equals[True# !, False]
      Equals[True# || [False]# !, False]
      Equals[True# && [True]# !, False]
    }

    it("should select the former") {
      Equals[True# ?[X, A, B], A]
      Equals[True# || [False]# ?[X, A, B], A]
      Equals[True# && [True]# ?[X, A, B], A]
    }
  }

  describe("False") {
    it("should be true iff the other is true on disjunction") {
      Equals[False# || [True], True]
      Equals[False# || [False], False]
      Equals[False# || [True# || [True]], True]
      Equals[False# || [True# && [False]], False]
      Equals[True# || [True]# || [False], True]
      Equals[True# && [False]# || [False], False]
    }

    it("should always be false on conjunction") {
      Equals[False# && [True], False]
      Equals[False# && [False], False]
      Equals[False# && [True# || [True]], False]
      Equals[False# && [True# && [False]], False]
      Equals[True# || [True]# && [False], False]
      Equals[True# && [False]# && [True], False]
    }

    it("should invert to true") {
      Equals[False# !, True]
      Equals[False# || [False]# !, True]
      Equals[False# && [True]# !, True]
    }

    it("should select the latter") {
      Equals[False# ?[X, A, B], B]
      Equals[False# || [False]# ?[X, A, B], B]
      Equals[False# && [True]# ?[X, A, B], B]
    }
  }
}
