/**
 * Created by Rokas on 07/05/2016.
 */

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CompressSuite extends FunSuite{
  import MyCompressor.compress

  test("compress: Single objects only"){

    val result = compress(List(1, 2, 3))

    assert(result.length == 3)
    for(resultElem <- result){
      assert(resultElem.isInstanceOf[Compressed])

    }
  }

}

