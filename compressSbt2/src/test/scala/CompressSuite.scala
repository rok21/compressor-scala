/**
 * Created by Rokas on 07/05/2016.
 */

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CompressSuite extends FunSuite{
  import RLECompressor.compress

  test("compress: empty sequence") {

    val param: Seq[String] = List()

    val result = compress(param)

    assert(result.length == 0)
  }

  test("compress: Single objects only: ints"){

    val param : Seq[Int] = List(1, 2, 3)

    val result = compress(param)

    assert(result.length == 3)
    for(i <- param.indices){
      assert(result(i).asInstanceOf[Single[Int]].element.equals(param(i)))
    }
  }

  test("compress: Single objects only: Strings") {

    val param: Seq[String] = List("AB", "A", "AA")

    val result = compress(param)

    assert(result.length == 3)
    for (i <- param.indices) {
      assert(result(i).asInstanceOf[Single[String]].element.equals(param(i)))
    }
  }

  test("compress: Single objects only: one element") {

    val param: Seq[String] = List("AB")

    val result = compress(param)

    assert(result.length == 1)
    assert(result(0).asInstanceOf[Single[String]].element.equals(param(0)))
  }

  test("compress: Repeat: example from wiki"){
    val param: Seq[Char] = List('W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'B', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'B', 'B', 'B', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'B', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W')

    val result = compress(param)

    //12W 1B 12W 3B 24W 1B 14W
    assert(result.length == 7)

    assert(result(0).asInstanceOf[Repeat[Char]].count==12)
    assert(result(0).asInstanceOf[Repeat[Char]].element=='W')

    assert(result(1).asInstanceOf[Single[Char]].element=='B')

    assert(result(2).asInstanceOf[Repeat[Char]].count==12)
    assert(result(2).asInstanceOf[Repeat[Char]].element=='W')

    assert(result(3).asInstanceOf[Repeat[Char]].element=='B')
    assert(result(3).asInstanceOf[Repeat[Char]].count==3)

    assert(result(4).asInstanceOf[Repeat[Char]].element=='W')
    assert(result(4).asInstanceOf[Repeat[Char]].count==24)

    assert(result(5).asInstanceOf[Single[Char]].element=='B')

    assert(result(6).asInstanceOf[Repeat[Char]].element=='W')
    assert(result(6).asInstanceOf[Repeat[Char]].count==14)
  }

  test("compress: Repeat: single repeat"){
    val param : Seq[Int] = List(1, 1, 1)

    val result = compress(param)

    assert(result.length == 1)
    assert(result(0).asInstanceOf[Repeat[Int]].count==3)
    assert(result(0).asInstanceOf[Repeat[Int]].element==1)
  }
}

