package tk.monnef.sprout

import com.avsystem.commons.misc.Opt
import com.avsystem.commons.serialization.{GenCodec, InputType, ListOutput}
import com.avsystem.commons.serialization.GenCodec.{create, createList, read, write}
import io.udash._
import io.udash.rest.DefaultServerREST
import io.udash.wrappers.jquery._
import org.scalajs.dom.{Element, document}
import tk.monnef.sprout.styles.{ArticleListStyles, ArticleStyles, CustomStyles, NewsListStyles}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Context {
  implicit val executionContext = scalajs.concurrent.JSExecutionContext.Implicits.queue
  private val routingRegistry = new RoutingRegistryDef
  private val viewPresenterRegistry = new StatesToViewPresenterDef

  implicit val applicationInstance = new Application[RoutingState](routingRegistry, viewPresenterRegistry, RootState)

  private val ApiHost = "localhost"
  private val ApiPort = 8022

  /**
    * Udash Option JSON codec for (de)serializing it this way:
    * Some(x) will be serialized as x.
    * None will be serialized as null.
    */
  implicit def optionCodec[T: GenCodec]: GenCodec[Option[T]] =
    create[Option[T]](
      i => i.inputType match {
        case InputType.Null =>
          i.readNull()
          None
        case _ =>
          Some(read[T](i))
      },
      locally {
        case (o, Some(t)) => write[T](o, t)
        case (o, None) => o.writeNull()
      }
    )

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
        jQ(NewsListStyles.render[TypedTag[org.scalajs.dom.raw.HTMLStyleElement]].render).insertBefore(appRoot.get)
      }
    })
  }
}
