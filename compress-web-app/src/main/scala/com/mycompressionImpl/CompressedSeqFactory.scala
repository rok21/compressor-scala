package com.mycompressionImpl

import com.example.compression.CompressedSeq

/**
 * Created by Rokas on 07/05/2016.
 */
object CompressedSeqFactory{
  def build[A] : (Seq[A]) => CompressedSeq[A] =
    (elems) => new RLECompressedSeq[A](RLECompressor.compress(elems))
}