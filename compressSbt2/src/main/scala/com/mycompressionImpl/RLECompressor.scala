package com.mycompressionImpl

import com.example.compression.{Compressed, Compressor, Repeat, Single}

/**
 * Created by Rokas on 07/05/2016.
 */

object RLECompressor extends Compressor{
    override def compress[A]: (Seq[A]) => Seq[Compressed[A]] = {

      def repeatToCompressed(repeatObj: Repeat[A]) : Compressed[A] = {
        if(repeatObj.count == 1){
          new Single[A](repeatObj.element)
        }else{
          repeatObj
        }
      }

      def compressMain(paramSeq : Seq[A]) : Seq[Compressed[A]] = {
        var compressedSeq = List[Compressed[A]]()
        var currentRepeat: Repeat[A] = null

        for (i <- paramSeq.indices) {
          var latestCompressed: Compressed[A] = null
          if (currentRepeat == null) { currentRepeat = new Repeat[A](0, paramSeq(i)) }
          currentRepeat = new Repeat[A](currentRepeat.count+1, currentRepeat.element)

          if (i== (paramSeq.length-1) || !paramSeq(i+1).equals(currentRepeat.element)) {
            //last or next one is different
            latestCompressed = repeatToCompressed(currentRepeat)
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

  override def decompress[A]: (Seq[Compressed[A]]) => Seq[A] = {
    def compressedToSeq(compressed : Compressed[A]): Seq[A] ={
      compressed match {
        case repeat: Repeat[A] =>
          List.fill(repeat.count)(repeat.element)
        case _ =>
          List(compressed.asInstanceOf[Single[A]].element)
      }
    }

    (paramSeq) => paramSeq.flatMap(compressedToSeq)
  }
}