package tk.monnef.sprout.styles

import scala.language.postfixOps
import scalacss.internal.{Attr, Literal}
import scalacss.Defaults._
import tk.monnef.sprout.styles.constants.StyleConstants
import tk.monnef.sprout.styles.fonts.UdashFonts

object CustomStyles extends StyleSheet.Inline {

  import dsl._

  val headerText = style(
    textAlign.center,
    padding(0.5 em),
    opacity(0.33),
    fontSize(120 %%)
  )
}

object ArticleListStyles extends StyleSheet.Inline {

  import dsl._

  val caption = style(
    fontSize(1.3 em),
    paddingBottom(0.5 em)
  )

  val body = style(
    paddingBottom(1 em),
    textAlign.justify
  )
}

object ArticleStyles extends StyleSheet.Inline {

  import dsl._

  val author = style(
    marginBottom(1 em)
  )
  val perex = style(
    marginBottom(1 em)
  )
  // RS
  val body = style(
    unsafeChild(".rs-img-center")(
      margin(1 em, auto, 0.5 em, auto)
    ),
    unsafeChild("pre")(
      padding(1 em),
      margin(1 em, 0 em, 1 em, 0 em),
      color(StyleConstants.Colors.codeTextColor),
      backgroundColor(StyleConstants.Colors.codeBackgroundColor),
      UdashFonts.overpassMono()
    ),
    unsafeChild("h2")(
      margin(1 em, 0 em, 0.5 em, 0 em)
    ),
    unsafeChild(".table")(
      margin(1 em, 1 em)
    )
  )
}
