package lemondrops


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class WhyScalaNeverWillMakeIt extends FunSuite {

  test("Array becomes ArraySeq when you least want it") {
    val arr = Array("A", "B", "C")
    def f: String => Dummy = new Dummy(_)

    val bucket = new Bucket[Dummy](arr.map(f))
//    val bucket2 = Bucket[String, Dummy](arr, f)
    val bucket3 = Bucket[Dummy](arr, f)
    val bucket4 = Bucket(arr, f)
  }

  class Bucket[T]( val arr: Array[T] )  {/* Loads of business logic */}

  object Bucket {                        /* Of course, we want factory methods, so we define a companion object */
//    def apply[T, U](arr: Array[T], f:T=>U):Bucket[U] = new Bucket[U](arr.map( b => f(b) ) )
    def apply[T: ClassManifest](arr: Array[String], f:String=>T):Bucket[T] = new Bucket[T](arr.map( b => f(b) ) )
/*
error: type mismatch;
found   : scala.collection.mutable.ArraySeq[T]
required: Array[T]
def apply[T](arr: Array[String], f:String=>T):Bucket[T]      = new Bucket[T](arr.map( b => f(b) ) )
*/

    def apply(arr: Array[String], f:String=>Dummy):Bucket[Dummy] = new Bucket[Dummy](arr.map( f ) )
  }


  class Dummy(val name: String)
}
