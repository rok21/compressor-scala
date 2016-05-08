package com.example.compression
trait CompressedSeq[+A] extends Seq[A] {
  def underlying: Seq[Compressed[A]]
}

