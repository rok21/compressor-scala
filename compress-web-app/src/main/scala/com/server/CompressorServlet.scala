package com.server

import com.example.compression.{Single, Repeat, Compressed, CompressedSeq}
import com.mycompressionImpl.{CompressedSeqFactory, RLECompressedSeq}
import org.scalatra._

class CompressorServlet extends CompressWebAppStack {

  var sequence : CompressedSeq[String] = null

  post("/add"){
    val postData = params("")
    val list = postDataToList(postData)
    sequence = CompressedSeqFactory.build(list)
  }

  get("/:id") {
    sequence(params("id").toInt)
  }

  get("/list") {
    sequence.underlying.map(compressedToString).mkString("\n")
  }

  def postDataToList : (String) => Seq[String] = param => param.split('\n')

  def compressedToString : (Compressed[String]) => String = {
    case repeat: Repeat[String] => repeat.count + repeat.element
    case single: Single[String] => 1 + single.element
  }


  get("/") {
    <html>
      <body>
        <h1>Yo!</h1>
        POST /add <br/>
        request body contains one or more elements (newline delimited)
        returns empty response<br/><br/>
        GET  / &lt;idx&gt; <br/>
        returns element at specified 0-based index<br/><br/>
        GET  /list<br/>
        returns run-length encoded list of all elements (newline delimited)<br/><br/>
      </body>
    </html>
  }
}
