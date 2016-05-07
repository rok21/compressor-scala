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

object RLECompressor extends Compressor{
    override def compress[A]: (Seq[A]) => Seq[Compressed[A]] = {
      def compressMain(paramSeq : Seq[A]) : Seq[Compressed[A]] = {
        var compressedSeq = List[Compressed[A]]()
        var currentRepeat: Repeat[A] = null

        for (i <- paramSeq.indices) {
          var latestCompressed: Compressed[A] = null
          if (currentRepeat == null) { currentRepeat = new Repeat[A](0, paramSeq(i)) }
          currentRepeat = new Repeat[A](currentRepeat.count+1, currentRepeat.element)

          if (i== (paramSeq.length-1) || !paramSeq(i+1).equals(currentRepeat.element)) {
            //last or next one is different
            if (currentRepeat.count > 1) {
              latestCompressed = currentRepeat
            } else {
              latestCompressed = new Single[A](currentRepeat.element)
            }
            currentRepeat = null
          }

          if(latestCompressed != null){
            compressedSeq = compressedSeq :+ latestCompressed
          }
        }
        compressedSeq
      }
      compressMain
    }

  override def decompress[A]: (Seq[Compressed[A]]) => Seq[A] = ???
}