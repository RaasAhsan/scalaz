package scalaz

import std.AllInstances._
import syntax.equal._
import scalaz.scalacheck.ScalazProperties._
import scalaz.scalacheck.ScalazArbitrary._
import org.scalacheck.Prop.forAll

object DListTest extends SpecLite {

  checkAll(equal.laws[DList[Int]])
  checkAll(monoid.laws[DList[Int]])
  checkAll(monadPlus.laws[DList])
  "DList append" ! ((0 to 100000).foldLeft(DList[Int]())(_ :+ _).toList must_== (0 to 100000).toList)

  "headOption, tailOption" ! forAll { (n: Int, d: DList[Int]) =>

    // Defined when appropriate?
    val nonempty = d.uncons(false, (_, _) => true)
    d.headOption.isDefined == nonempty
    d.tailOption.isDefined == nonempty

    // If defined, are values correct?
    val d0 = n +: d
    d0.headOption === Some(n)
    d0.tailOption === Some(d)
    
  }

}
