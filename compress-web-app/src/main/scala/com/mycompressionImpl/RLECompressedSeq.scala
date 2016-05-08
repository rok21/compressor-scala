package com.mycompressionImpl

import com.example.compression.{Repeat, Compressed, Compressor, CompressedSeq}

/**
 * Created by Rokas on 07/05/2016.
 */
class RLECompressedSeq[A](compressedElems : Seq[Compressed[A]]) extends CompressedSeq[A]{

  override def underlying: Seq[Compressed[A]] = {
    compressedElems
  }

  var len : Int = -1
  override def length: Int = {
    if(len== -1) {
      len = compressedElems.map(compressedToLen).sum
    }
    len
  }

  override def apply(idx: Int): A = {
    var compressedIdx = 0
    var decompressedIdx : Int = 0
    while(decompressedIdx < idx){
      decompressedIdx+= compressedToLen(compressedElems(compressedIdx))
      compressedIdx+=1
    }
    if(decompressedIdx>idx){
      compressedIdx-=1
    }
    return RLECompressor.decompress(List(compressedElems(compressedIdx))).head
  }

  override def iterator: Iterator[A] = new RLEIterator(compressedElems)

  class RLEIterator[A](compressedElems : Seq[Compressed[A]]) extends Iterator[A] {

    var nextCompressedPosition = 0
    var currentDecompressedPosition = 0
    var currentDecompressedCount = 0
    var decompressedElement : A = null.asInstanceOf[A]

    override def hasNext: Boolean = {
      currentDecompressedPosition < currentDecompressedCount || nextCompressedPosition < compressedElems.length
    }

    override def next(): A = {
      if(currentDecompressedPosition == currentDecompressedCount){
        decompressedElement = decompressNext
        currentDecompressedPosition= 0
      }
      currentDecompressedPosition += 1
      decompressedElement
    }

    def decompressNext : A = {
      val toDecompress = compressedElems(nextCompressedPosition)
      currentDecompressedCount = compressedToLen(toDecompress)
      nextCompressedPosition+=1
      RLECompressor.decompress(List(toDecompress)).head
    }
  }

  private def compressedToLen : (Compressed[Any]) => Int =
  {
    case repeat: Repeat[Any] => repeat.count
    case _ => 1
  }
}






