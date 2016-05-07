/**
 * Created by Rokas on 07/05/2016.
 */

import com.mycompressionImpl.{CompressedSeqFactory}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
@RunWith(classOf[JUnitRunner])
class SequenceSuite extends FunSuite{

  test("sequence : underlying"){
    val list = List(1, 1, 3, 4)

    val compressedSeq = CompressedSeqFactory.build(list)

    assert(compressedSeq.underlying.length == 3)
  }

  test("sequence : length"){
    val list = List(1, 1, 3, 4)
    val compressedSeq = CompressedSeqFactory.build(list)

    assert(compressedSeq.length == 4)
  }

  test("sequence : apply"){
    val list = List("ABC", "ABC", "A", "A")

    val compressedSeq = CompressedSeqFactory.build(list)

    for(i <- compressedSeq.indices){
      assert(list(i).equals(compressedSeq(i)))
    }
  }

  test("sequence : iterator") {
    val list = List("ABC", "ABC", "A", "A", "ABC")

    val compressedSeq = CompressedSeqFactory.build(list)

    assert(compressedSeq.iterator.length == 5)
    val iterator = compressedSeq.iterator
    for(elem <- list){
      assert(iterator.hasNext)
      assert(iterator.next == elem)
    }
  }

  test("sequence : empty sequence"){
    val compressedSeq = CompressedSeqFactory.build(List())
    assert(compressedSeq.length == 0)
    assert(compressedSeq.iterator.length == 0)
    assert(compressedSeq.underlying.length == 0)
  }
}
