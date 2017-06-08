package util

import play.twirl.api.Html
import com.googlecode.htmlcompressor.compressor.HtmlCompressor

// https://stackoverflow.com/questions/14154671/is-it-possible-to-prettify-scala-templates-using-play-framework-2
object HtmlUtil {

  lazy val compressor = new HtmlCompressor()

  def prettify(content: Html): Html = {
    val rawString = content.body.trim()
    compressor.setPreserveLineBreaks(true)
    Html(compressor.compress(rawString))
  }

}
