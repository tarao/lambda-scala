Type level lambda calculus in Scala
===================================

This repository demonstrates an implementation of lambda calculus in
Scala types.

Untyped lambda calculus
-----------------------

```scala
import lambda._
case class Equals[A >: B <: B, B]() // this checks type equality

type S = x ->: y ->: z ->: ( x @@ z @@ (y @@ z) )
type K = x ->: y ->: x

type result = ( S @@ K @@ K @@ a ) # ->*
Equals[result, a]
```

More examples are found in [TermSpec.scala][example].

### Syntax

|                         |Code              |
|:------------------------|:-----------------|
|Variables                |`a`, `b`, ..., `z`|
|Abstraction              |`v ->: M`         |
|Application              |`M @@ N`          |
|Terms                    |`M`, `N`          |
|1-step beta reduction    |`M # ->`          |
|Multi-step beta reduction|`M # ->*`         |

- You can define your own variables by calling `#next` of existing (last defined) variable

  ```scala
  type variableName1 = z #next
  type variableName2 = variableName1 #next
  ```

- You may need parenthesis to avoid ambiguity

### Capture-avoiding substitutions

Lambda terms are converted internally to
[De Bruijn indexed terms][debruijn] and indices are shifted during
substitution to ensure capture-avoiding semantics.

License
-------

- Copyright (C) INA Lintaro
- MIT License

References
----------

- [Scala type level encoding of the SKI calculus][ski]
- [Type-Level Programming in Scala][typelevel]

[example]: src/test/scala/lambda/TermSpec.scala
[debruijn]: http://en.wikipedia.org/wiki/De_Bruijn_index
[ski]: https://michid.wordpress.com/2010/01/29/scala-type-level-encoding-of-the-ski-calculus/
[typelevel]: https://apocalisp.wordpress.com/2010/06/08/type-level-programming-in-scala/
