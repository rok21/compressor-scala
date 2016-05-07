/**
 * Created by Rokas on 07/05/2016.
 */
sealed trait Compressed[+A]
case class Single[A](element: A) extends Compressed[A]
case class Repeat[A](count: Int, element: A) extends Compressed[A]

trait Compressor {
  def compress[A]: Seq[A] => Seq[Compressed[A]]
  def decompress[A]: Seq[Compressed[A]] => Seq[A]
}

object MyCompressor extends Compressor{
  override def compress[A]: (Seq[A]) => Seq[Compressed[A]] = {
    def compressInner(paramSeq : Seq[A]) : Seq[Compressed[A]] = {
      var compressedSeq = List[Compressed[A]]()
      for(elem <- paramSeq){
        compressedSeq = compressedSeq :+ new Single[A](elem)
      }
      compressedSeq
    }
    compressInner
  }

  override def decompress[A]: (Seq[Compressed[A]]) => Seq[A] = ???
}