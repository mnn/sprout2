package tk.monnef.sprout.views

import scala.util.{Failure, Success}
import cats._
import cats.data._
import cats.implicits._
import tk.monnef.sprout.views.components.{Articles, ArticlesList}

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

case class ArticleModel(
                         article: Option[Article],
                         articleUrl: String
                       )

case class ArticleViewPresenter(articleUrl: String) extends ViewPresenter[ArticleState] {

  import tk.monnef.sprout.Context._

  override def create(): (View, Presenter[ArticleState]) = {
    val model = ModelProperty(ArticleModel(None, articleUrl))
    val presenter = new ArticlePresenter(model)
    (new ArticleView(model, presenter), presenter)
  }
}

class ArticlePresenter(model: ModelProperty[ArticleModel]) extends Presenter[ArticleState] {
  override def handleState(state: ArticleState): Unit = {
    model.subProp(_.article).set(None)
    import Context._
    Context.restServer.article(model.get.articleUrl).onComplete {
      case Success(response) =>
        model.subProp(_.article).set(response.some)
      case Failure(ex) => println("failed to get an article: ", ex)
    }
  }
}

class ArticleView(model: ModelProperty[ArticleModel], presenter: ArticlePresenter) extends View {

  import tk.monnef.sprout.Context._
  import scalatags.JsDom.all._

  private val content = div(BootstrapStyles.container)(id := "content",
    div(CustomStyles.article)(
      produce(model)((m: ArticleModel) => {
        m.article.map((a: Article) => {
          div(
            h1(a.name),
            div(a.author.name + " | " + a.date),
            div(a.perex),
            div(raw(a.body))
          )
        }).getOrElse(div("???")).render
      })
    )
  )

  override def renderChild(view: View): Unit = {}

  override def getTemplate: Modifier = content
}