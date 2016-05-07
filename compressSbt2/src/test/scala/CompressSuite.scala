/**
 * Created by Rokas on 07/05/2016.
 */

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CompressSuite extends FunSuite{
  import RLECompressor.compress

  test("compress: Single objects only"){

    val param : Seq[Int] = List(1, 2, 3)

    val result = compress(param)

    assert(result.length == 3)
    for(i <- param.indices){
      assert(result(i).isInstanceOf[Compressed[Int]])
      assert(result(i).asInstanceOf[Single[Int]].element.equals(param(i)))
    }
  }

}

