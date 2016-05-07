/**
 * Created by Rokas on 07/05/2016.
 */
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import com.example.compression.{Repeat, Single, Compressed}

@RunWith(classOf[JUnitRunner])
class DecompressSuite extends FunSuite {

  import RLECompressor.decompress

  test("decompress: empty sequence") {

    val param: Seq[Compressed[Any]] = List()

    val result = decompress(param)

    assert(result.length == 0)
  }

  test("decompress: single object") {
    val param: Seq[Compressed[Any]] = List(
      new Single[Any]("A")
    )
    val result = decompress(param)
    assert(result.length == 1)
    assert(result(0) == "A")
  }

  test("decompress: repeat and single") {

    val param: Seq[Compressed[Any]] = List(
      new Repeat[Any](5, "ABC"), new Single[Any](99.9), new Repeat[Any](2, "CBA")
    )

    val result = decompress(param)

    assert(result.length == 8)
    for(i <- 0 until 5){
      assert(result(i).equals("ABC"))
    }
    assert(result(5).equals(99.9))
    for(i <- 6 until 8){
      assert(result(i).equals("CBA"))
    }
  }

  test("decompress: compressed twice") {

    val compressed: Seq[Compressed[Any]] = List(
      new Repeat[Any](5, "ABC"), new Single[Any](99.9), new Repeat[Any](2, "CBA")
    )

    val param : Seq[Compressed[Any]] = List(new Single[Any](compressed))

    val result = decompress(param)
    assert(result.length == 1)
    assert(result(0).equals(compressed))
  }
}
