package lambda

class TermSpec extends helper.UnitSpec {
  describe("A lambda term") {
    it("should reduce to a value if normalizing") {
      Equals[ a# ->*, a ]
      Equals[ (a ->: a)# ->*, a ->: a ]
      Equals[ ( (a ->: a) @@ a )# ->*, a ]
      Equals[ ( (a ->: a) @@ b )# ->*, b ]

      type S = x ->: y ->: z ->: ( x @@ z @@ (y @@ z) )
      type K = x ->: y ->: x
      Equals[ (S @@ K @@ K)# ->*, z ->: z ]

      type SSKSKI = (S @@ ((S @@ (K @@ S)) @@ K)) @@ (S @@ K @@ K)
      Equals[ SSKSKI# ->*, z ->: z ->: (z @@ (z @@ z)) ]
      Equals[ (SSKSKI @@ a @@ b)# ->*, a @@ (a @@ b) ]

      // Leftmost strategy is normalizing
      type O = a ->: (a @@ a)
      Equals[ ((K @@ c) @@ (O @@ O))# ->*, c ]

      type _0 = s ->: z ->: z
      type _1 = s ->: z ->: (s @@ z)
      type _2 = s ->: z ->: (s @@ (s @@ z))
      type _3 = s ->: z ->: (s @@ (s @@ (s @@ z)))

      type T = a ->: b ->: a
      type F = a ->: b ->: b
      type if0 = a ->: (a @@ (b ->: F) @@ T)
      type mul = m ->: n ->: s ->: z ->: (n @@ (m @@ s) @@ z)
      type pred = n ->: s ->: z ->:
        ((n @@ (f ->: g ->: (g @@ (f @@ s)))) @@ (x ->: z) @@ (x ->: x))
      type fact = r ->: n ->:
        (if0 @@ n @@ _1 @@ (mul @@ n @@ (r @@ (pred @@ n))))
      type yy = x ->: (f @@ (x @@ x))
      type Y = f ->: (yy @@ yy)

      Equals[ (if0 @@ _0 @@ a @@ b)# ->*, a ]
      Equals[ (if0 @@ _1 @@ a @@ b)# ->*, b ]
      Equals[
        (mul @@ _2 @@ _2)# ->*,
        s ->: z ->: (s @@ (s @@ (s @@ (s @@ z))))
      ]
      Equals[ (pred @@ _2)# ->*, _1 ]

      Equals[
        (Y @@ fact @@ _1)# ->*,
        s ->: z ->: (s @@ z)
      ]

      // This takes super very long
      // Equals[
      //   (Y @@ fact @@ _2)# ->*,
      //   s ->: z ->: (s @@ (s @@ z))
      // ]
    }
  }
}
