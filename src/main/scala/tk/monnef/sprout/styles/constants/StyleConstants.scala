package tk.monnef.sprout.styles.constants

import scalacss.Defaults._

object StyleConstants extends StyleSheet.Inline {

  import dsl._

  /**
    * SIZES
    */
  object Sizes {
    val BodyWidth = 1075

    val MinSiteHeight = 550

    val HeaderHeight = 80

    val HeaderHeightMobile = 80

    val FooterHeight = 120

    val MobileMenuButton = 50
  }

  /**
    * COLORS
    */
  object Colors {
    val Red = c"#e30613"

    val RedLight = c"#ff2727"

    val GreyExtra = c"#ebebeb"

    val GreySemi = c"#cfcfd6"

    val Grey = c"#777785"

    val Yellow = c"#ffd600"

    val Cyan = c"#eef4f7"

    // ^ from Udash generator
    val golden = c"#bfbc9f"
    val lightRed = c"#d1a19a"
    val darkBlue = c"#020919"
    val lighterDarkBlue = c"#0f1522"

    val linkColor = c"#C2D4F9"

    val bodyBackgroundColor = darkBlue
    val bodyTextColor = c"#9FA9BF"
    val bodyGhostTextColor = c"#414C62"

    val codeTextColor = lightRed
    val codeBackgroundColor = c"#000000"

    val strongColor = golden

    val captionColor = golden
  }

  /**
    * MEDIA QUERIES
    */
  object MediaQueriesBounds {
    val TabletLandscapeMax = Sizes.BodyWidth - 1

    val TabletLandscapeMin = 768

    val TabletMax = TabletLandscapeMin - 1

    val TabletMin = 481

    val PhoneMax = TabletMin - 1
  }

}