package tk.monnef.sprout

import io.udash._
import io.udash.rest.DefaultServerREST
import io.udash.wrappers.jquery._
import org.scalajs.dom.{Element, document}
import tk.monnef.sprout.styles.{ArticleListStyles, ArticleStyles, CustomStyles}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Context {
  implicit val executionContext = scalajs.concurrent.JSExecutionContext.Implicits.queue
  private val routingRegistry = new RoutingRegistryDef
  private val viewPresenterRegistry = new StatesToViewPresenterDef

  implicit val applicationInstance = new Application[RoutingState](routingRegistry, viewPresenterRegistry, RootState)

  private val ApiHost = "localhost"
  private val ApiPort = 8022
  val restServer = DefaultServerREST[StumpRest](ApiHost, ApiPort, "/api/")
  //  val restServer = DefaultServerREST[StumpRest](ApiHost, 3000)
}

object Init extends JSApp with StrictLogging {

  import Context._

  @JSExport
  override def main(): Unit = {
    jQ(document).ready((_: Element) => {
      val appRoot = jQ("#application").get(0)
      if (appRoot.isEmpty) {
        logger.error("Application root element not found! Check your index.html file!")
      } else {
        applicationInstance.run(appRoot.get)

        import scalacss.Defaults._
        import scalacss.ScalatagsCss._
        import scalatags.JsDom._
        import tk.monnef.sprout.styles.GlobalStyles
        import tk.monnef.sprout.styles.DemoStyles
        import tk.monnef.sprout.styles.partials.FooterStyles
        import tk.monnef.sprout.styles.partials.HeaderStyles
        jQ(GlobalStyles.render[TypedTag[org.scalajs.dom.raw.HTMLStyleElement]].render).insertBefore(appRoot.get)
        jQ(DemoStyles.render[TypedTag[org.scalajs.dom.raw.HTMLStyleElement]].render).insertBefore(appRoot.get)
        jQ(FooterStyles.render[TypedTag[org.scalajs.dom.raw.HTMLStyleElement]].render).insertBefore(appRoot.get)
        jQ(HeaderStyles.render[TypedTag[org.scalajs.dom.raw.HTMLStyleElement]].render).insertBefore(appRoot.get)
        jQ(CustomStyles.render[TypedTag[org.scalajs.dom.raw.HTMLStyleElement]].render).insertBefore(appRoot.get)
        jQ(ArticleListStyles.render[TypedTag[org.scalajs.dom.raw.HTMLStyleElement]].render).insertBefore(appRoot.get)
        jQ(ArticleStyles.render[TypedTag[org.scalajs.dom.raw.HTMLStyleElement]].render).insertBefore(appRoot.get)
      }
    })
  }
}
