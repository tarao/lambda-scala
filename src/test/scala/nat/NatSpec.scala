package nat

class NatSpec extends helper.UnitSpec {
  import bool.{True, False}

  describe("Nat") {
    it("should equal to the same represenation") {
      Equals[Z# ==[ Z ], True]
      Equals[S[Z]# ==[ S[Z] ], True]
      Equals[S[S[Z]]# ==[ S[S[Z]] ], True]
    }

    it("should not equal to a different represenation") {
      Equals[Z# ==[ S[Z] ], False]
      Equals[S[Z]# ==[ Z ], False]
      Equals[S[Z]# ==[ S[S[Z]] ], False]
      Equals[S[S[Z]]# ==[ Z ], False]
      Equals[S[S[Z]]# ==[ S[Z] ], False]
      Equals[S[S[Z]]# ==[ S[S[S[Z]]] ], False]
    }
  }

  describe("Zero") {
    it("should be zero") {
      Equals[Z#isZero, True]
    }

    it("should not be greater than any other value") {
      Equals[Z# > [Z], False]
      Equals[Z# > [S[Z]], False]
      Equals[Z# > [S[S[Z]]], False]
    }

    it("should increments to one") {
      Equals[Z# ++, S[Z]]
    }

    it("should decrements to zero") {
      Equals[Z# --, Z]
    }

    it("should unchange an operand on addition") {
      Equals[Z# + [Z], Z]
      Equals[Z# + [S[Z]], S[Z]]
      Equals[Z# + [S[S[Z]]], S[S[Z]]]
      Equals[Z# + [S[S[S[Z]]]], S[S[S[Z]]]]
    }
  }

  describe("Succ") {
    it("should not be zero") {
      Equals[S[Z]#isZero, False]
      Equals[S[S[Z]]#isZero, False]
    }

    it("should be greater than less values") {
      Equals[S[Z]# > [Z], True]
      Equals[S[Z]# > [S[Z]], False]
      Equals[S[Z]# > [S[S[Z]]], False]
      Equals[S[S[Z]]# > [Z], True]
      Equals[S[S[Z]]# > [S[Z]], True]
      Equals[S[S[Z]]# > [S[S[Z]]], False]
    }

    it("should decrements to a predecessor") {
      Equals[S[Z]# --, Z]
      Equals[S[S[Z]]# --, S[Z]]
      Equals[S[S[S[Z]]]# --, S[S[Z]]]
    }

    it("should accumulate to an operand on addition") {
      Equals[S[Z]# + [Z], S[Z]]
      Equals[S[Z]# + [S[Z]], S[S[Z]]]
      Equals[S[Z]# + [S[S[Z]]], S[S[S[Z]]]]
      Equals[S[Z]# + [S[S[S[Z]]]], S[S[S[S[Z]]]]]
      Equals[S[S[Z]]# + [Z], S[S[Z]]]
      Equals[S[S[Z]]# + [S[Z]], S[S[S[Z]]]]
      Equals[S[S[Z]]# + [S[S[Z]]], S[S[S[S[Z]]]]]
      Equals[S[S[Z]]# + [S[S[S[Z]]]], S[S[S[S[S[Z]]]]]]
      Equals[S[S[S[Z]]]# + [Z], S[S[S[Z]]]]
      Equals[S[S[S[Z]]]# + [S[Z]], S[S[S[S[Z]]]]]
      Equals[S[S[S[Z]]]# + [S[S[Z]]], S[S[S[S[S[Z]]]]]]
      Equals[S[S[S[Z]]]# + [S[S[S[Z]]]], S[S[S[S[S[S[Z]]]]]]]
    }
  }
}
