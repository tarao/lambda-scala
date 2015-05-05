package object lambda {
  type ? = impl.Var[nat.Z]
  type a = ? #next
  type b = a #next
  type c = b #next
  type d = c #next
  type e = d #next
  type f = e #next
  type g = f #next
  type h = g #next
  type i = h #next
  type j = i #next
  type k = j #next
  type l = k #next
  type m = l #next
  type n = m #next
  type o = n #next
  type p = o #next
  type q = p #next
  type r = q #next
  type s = r #next
  type t = s #next
  type u = t #next
  type v = u #next
  type w = v #next
  type x = w #next
  type y = x #next
  type z = y #next

  type @@[m <: Term, n <: Term] = App[m, n]
  type ->:[x <: Var, m <: Term] = Abs[x, m]
}
