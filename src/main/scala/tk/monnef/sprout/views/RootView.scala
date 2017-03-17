package tk.monnef.sprout.views

import io.udash._
import tk.monnef.sprout.RootState
import org.scalajs.dom.Element
import scalatags.JsDom.tags2.main
import tk.monnef.sprout.views.components.{Footer, Header}
import tk.monnef.sprout.styles.{DemoStyles, GlobalStyles}
import scalacss.ScalatagsCss._

object RootViewPresenter extends DefaultViewPresenterFactory[RootState.type](() => new RootView)

class RootView extends View {

  import tk.monnef.sprout.Context._
  import scalatags.JsDom.all._

  private val child: Element = div().render

  private val content = div(
    Header.getTemplate,
    main(GlobalStyles.main)(
      div(GlobalStyles.body)(
        child
      )
    )
    , Footer.getTemplate
  )

  override def getTemplate: Modifier = content

  override def renderChild(view: View): Unit = {
    import io.udash.wrappers.jquery._
    jQ(child).children().remove()
    view.getTemplate.applyTo(child)
  }
}
