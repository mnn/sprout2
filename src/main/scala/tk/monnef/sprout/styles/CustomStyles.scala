package tk.monnef.sprout.styles

import scala.language.postfixOps
import scalacss.internal.{Attr, Literal}
import scalacss.Defaults._

object CustomStyles extends StyleSheet.Inline {

  import dsl._

  val articlePreviewCaption = style(
    fontSize(1.3 em),
    paddingBottom(0.5 em)
  )

  val articlePreview = style(
    paddingBottom(1 em),
    textAlign.justify
  )

  val headerText = style(
    textAlign.center,
    padding(0.5 em),
    opacity(0.33),
    fontSize(120 %%)
  )
  
  val article = style(
    
  )
}
