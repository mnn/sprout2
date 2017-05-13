package tk.monnef.sprout.views

import scala.util.{Failure, Success}
import cats._
import cats.data._
import cats.implicits._
import tk.monnef.sprout.views.components.{Articles, ArticlesList, News, NewsList}

import scalatags.JsDom

//import cats.instances.all._

import io.udash._
import io.udash.ViewPresenter
import io.udash.bootstrap.BootstrapStyles
import io.udash.core.{DefaultViewPresenterFactory, Presenter}

import scala.scalajs.js
import scalacss.ScalatagsCss._
import org.scalajs.dom.Element

import js.JSConverters._
import scala.scalajs.js.Dynamic.global
import tk.monnef.sprout.styles.{DemoStyles, GlobalStyles, CustomStyles}
import tk.monnef.sprout._
import tk.monnef.sprout.IndexState

import tk.monnef.sprout.utils._

case class IndexModel(articles: ArticlesList, news: NewsList)

object IndexViewPresenter extends ViewPresenter[IndexState.type] {

  import tk.monnef.sprout.Context._

  override def create(): (View, Presenter[IndexState.type]) = {
    val model = ModelProperty(IndexModel(ArticlesList(Seq()), NewsList(Seq())))
    val presenter = new IndexPresenter(model)
    (new IndexView(model, presenter), presenter)
  }
}

class IndexPresenter(model: ModelProperty[IndexModel]) extends Presenter[IndexState.type] {
  override def handleState(state: IndexState.type): Unit = {
    model.subProp(_.articles).set(ArticlesList(Seq()))
    model.subProp(_.news).set(NewsList(Seq()))
    import Context._
    Context.restServer.articles().onComplete {
      case Success(response) => model.subModel(_.articles).subProp(_.articles).set(response)
      // global.console.log("img urls:", response.map(_.imageUrl).toJSArray)
      case Failure(ex) => println("failed to get articles: ", ex)
    }
    Context.restServer.news().onComplete {
      case Success(response) => model.subModel(_.news).subProp(_.news).set(response)
      case Failure(ex) => println("failed to get news: ", ex)
    }
  }
}

class IndexView(model: Property[IndexModel], presenter: IndexPresenter) extends View {

  import tk.monnef.sprout.Context._
  import scalatags.JsDom.all._

  private val content = div(BootstrapStyles.container)(id := "content",
    div(BootstrapStyles.row)(
      div(BootstrapStyles.Grid.colXs12, BootstrapStyles.Grid.colMd8)(
        Articles.getTemplate(model.transform(_.articles))
      ),
      div(BootstrapStyles.Grid.colXs12, BootstrapStyles.Grid.colMd4)(
        div(BootstrapStyles.Visibility.hiddenXs, BootstrapStyles.Visibility.visibleMd, style := "width: 1em"),
        div()(
          News.getTemplate(model.transform(_.news))
        )
      )
    )
  )

  override def renderChild(view: View): Unit = {}

  override def getTemplate: Modifier = content
}
