package lambda

class DeBruijnTermSpec extends helper.UnitSpec {
  describe("A De Bruijn indexed term") {
    import nat.{S, Z}
    import lambda.{debruijn => D}
    import D.{IndexedVar => V}

    type _0 = b ->: a ->: a
    type T = a ->: b ->: a
    type F = b ->: a ->: a
    type if0 = a ->: (a @@ (b ->: F) @@ T)

    it("should correctly be converted from a lambda term") {
      Equals[ a#toDebruijn, V[Z, a] ]
      Equals[ (a ->: a)#toDebruijn, D.Abs[a, V[S[Z], a]] ]
      Equals[ (a ->: a ->: a)#toDebruijn, D.Abs[a, D.Abs[a, V[S[Z], a]]] ]
      Equals[ (a ->: b ->: a)#toDebruijn, D.Abs[a, D.Abs[b, V[S[S[Z]], a]]] ]
      Equals[
        (a ->: a ->: (a @@ a))#toDebruijn,
        D.Abs[a, D.Abs[a, D.App[ V[S[Z], a], V[S[Z], a] ]]]
      ]
      Equals[
        (a ->: b ->: (a @@ b))#toDebruijn,
        D.Abs[a, D.Abs[b, D.App[ V[S[S[Z]], a], V[S[Z], b] ]]]
      ]
      Equals[
        ( (a ->: b ->: a) @@ (b ->: b) )#toDebruijn,
        D.App[D.Abs[a, D.Abs[b, V[S[S[Z]], a]]], D.Abs[b, V[S[Z], b]]]
      ]
      Equals[
        ( b ->: ((a ->: b ->: (a @@ b)) @@ b) )#toDebruijn,
        D.Abs[b, D.App[
          D.Abs[a, D.Abs[b, D.App[V[S[S[Z]], a], V[S[Z], b]]]],
          V[S[Z], b]
        ]]
      ]

      Equals[
        (if0 @@ _0)#toDebruijn,
        D.App[
          D.Abs[a, D.App[
            D.App[
              V[S[Z], a],
              D.Abs[b, D.Abs[b, D.Abs[a, V[S[Z], a]]]]
            ],
            D.Abs[a, D.Abs[b, V[S[S[Z]], a]]]
          ] ],
          D.Abs[b, D.Abs[a, V[S[Z], a]]]
        ]
      ]
    }

    it("should reduce correctly without shift") {
      Equals[ a#toDebruijn# ->, V[Z, a] ]
      Equals[ (a ->: a)#toDebruijn# ->, D.Abs[a, V[S[Z], a]] ]
      Equals[
        ( (a ->: b ->: a) @@ (b ->: b) )#toDebruijn# ->,
        D.Abs[b, D.Abs[b, V[S[Z], b]] ]
      ]
    }

    it("should reduce correctly with shift") {
      Equals[
        ( b ->: ((a ->: b ->: (a @@ b)) @@ b) )#toDebruijn# ->,
        D.Abs[b, D.Abs[b, D.App[V[S[S[Z]], b], V[S[Z], b]]]]
      ]

      Equals[
        (if0 @@ _0)#toDebruijn# ->,
        D.App[
          D.App[
            D.Abs[b, D.Abs[a, V[S[Z], a]]],
            D.Abs[b, D.Abs[b, D.Abs[a, V[S[Z], a]]]]
          ],
          D.Abs[a, D.Abs[b, V[S[S[Z]], a]]]
        ]
      ]
      Equals[
        (if0 @@ _0)#toDebruijn# -> # ->,
        D.App[D.Abs[a, V[S[Z], a]], D.Abs[a, D.Abs[b, V[S[S[Z]], a]]]]
      ]
      Equals[
        (if0 @@ _0)#toDebruijn# -> # -> # ->,
        D.Abs[a, D.Abs[b, V[S[S[Z]], a]]]
      ]
    }
  }
}
