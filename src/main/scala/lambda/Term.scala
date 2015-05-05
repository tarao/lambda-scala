package lambda

import scala.language.higherKinds
import nat.{Nat, S, Z}
import bool.{Bool, True, False}

trait Term {
  type toDebruijnByIndex[index <: impl.List] <: debruijn.Term
  type toDebruijn = toDebruijnByIndex[impl.Nil]
  type -> = toDebruijn# -> # toLambda
  type ->* = toDebruijn# ->* # toLambda
}

trait Var extends Term {
  type ord <: Nat
  type ==[other <: Var] <: Bool
}

trait App[m <: Term, n <: Term] extends Term {
  type toDebruijnByIndex[index <: impl.List] =
    debruijn.App[m#toDebruijnByIndex[index], n#toDebruijnByIndex[index]]
}

trait Abs[x <: Var, m <: Term] extends Term {
  type toDebruijnByIndex[index <: impl.List] =
    debruijn.Abs[x, m#toDebruijnByIndex[impl.Cons[x, index]]]
}

package debruijn {
  trait Term {
    type toLambda <: lambda.Term
    type subst[t <: Term, level <: Nat] <: Term
    type -> <: Term
    type ->* <: Term

    type isAbs <: Bool
    type hasRedex <: Bool
    type inc[shift <: Nat, level <: Nat] <: Term
    type apply1[t <: Term] <: Term
    type apply[t <: Term] <: Term
  }

  trait Var extends Term {
    type isAbs = False
    type hasRedex = False
    type inc[shift <: Nat, level <: Nat] <: Var
  }

  trait IndexedVar[i <: Nat, v <: lambda.Var] extends Var {
    type toLambda = v
    type subst[t <: Term, l <: Nat] = i# == [l]# ?[Term,
      t#inc[i# --, Z],
      IndexedVar[i# > [l]# ?[Nat, i# --, i], v]
    ]
    type -> = IndexedVar[i, v]
    type ->* = IndexedVar[i, v]

    type inc[s <: Nat, l <: Nat] =
      IndexedVar[i# > [l]# ?[Nat, i# + [s], i], v]
    type apply1[t <: Term] = App[IndexedVar[i, v], t]
    type apply[t <: Term] = apply1[t# ->*]
  }

  trait App[m <: Term, n <: Term] extends Term {
    type toLambda = lambda.@@[m#toLambda, n#toLambda]
    type subst[t <: Term, l <: Nat] = App[m#subst[t, l], n#subst[t, l]]
    type -> = m#isAbs# ?[Term,
      m#apply1[n],
      m#hasRedex# ?[Term, App[m# ->, n], App[m, n# ->]]
    ]
    type ->* = m# ->* # apply [ n ]

    type isAbs = False
    type hasRedex = m#isAbs# || [m#hasRedex# || [n#hasRedex]]
    type inc[s <: Nat, l <: Nat] = App[m#inc[s, l], n#inc[s, l]]
    type apply1[t <: Term] = App[App[m, n], t]
    type apply[t <: Term] = apply1[t]
  }

  trait Abs[x <: lambda.Var, m <: Term] extends Term {
    type toLambda = lambda.->:[x, m#toLambda]
    type subst[t <: Term, l <: Nat] = Abs[x, m#subst[t, l# ++]]
    type -> = Abs[x, m# ->]
    type ->* = Abs[x, m# ->*]

    type isAbs = True
    type hasRedex = m#hasRedex
    type inc[s <: Nat, l <: Nat] = Abs[x, m#inc[s, l# ++]]
    type apply1[t <: Term] = m#subst[t, S[Z]]
    type apply[t <: Term] = apply1[t]# ->*
  }
}

package impl {
  trait Var[n <: Nat] extends lambda.Var {
    type next = Var[S[n]]
    type ord = n
    type ==[other <: lambda.Var] = n# == [other#ord]
    type toDebruijnByIndex[index <: impl.List] =
      debruijn.IndexedVar[index#search[Var[n]], Var[n]]
  }

  trait List {
    type search[v <: lambda.Var] <: Nat
  }

  trait Nil extends List {
    type search[v <: lambda.Var] = Z
  }

  trait Cons[car <: lambda.Var, cdr <: List] extends List {
    trait ZeroOrSucc[n <: Nat] { type -> = n#isZero# ?[Nat, Z, n# ++] }
    type search[v <: lambda.Var] = car# == [v]# ?
      [Nat, S[Z], ZeroOrSucc[cdr#search[v]]# ->]
  }
}
