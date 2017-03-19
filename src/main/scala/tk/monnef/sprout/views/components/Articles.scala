package tk.monnef.sprout.views.components

import io.udash._
import io.udash.bootstrap.BootstrapStyles
import org.scalajs.dom
import org.scalajs.dom.raw.Element

import scalatags.JsDom.all._
import scalacss.ScalatagsCss._
import tk.monnef.sprout.styles.{ArticleListStyles, CustomStyles, DemoStyles, GlobalStyles}
import tk.monnef.sprout.{ArticlePreview, ArticleState, Model}
import tk.monnef.sprout.utils._

object Articles {

  import tk.monnef.sprout.Context._

  def getTemplate(model: ReadableProperty[ArticlesList]) =
    List(
      //div("Články (", bind(model.transform(_.articles.length)), ")"),
      produce(model.transform(_.articles))(x => x.map(article => {
        val authorPart = article.author.name.toNonEmptyOpt.map(span(ArticleListStyles.info)(_))
        val datePart = article.date.toNonEmptyOpt.map(span(_))
        val catPart = article.category.name.toNonEmptyOpt.map(span(_))
        val commPart = article.commentsCount.map(span(_))

        val colImg = div(BootstrapStyles.Grid.colSm3)(
          img(src := article.imageUrl)
        )
        val colText = div(BootstrapStyles.Grid.colSm9)(
          div(ArticleListStyles.caption)(
            a(href := ArticleState(article.urlEncoded).url, article.name),
            " | ",
            a(href := Model.baseUrl + "/" + article.url, target := "blank", "originál")
          ),
          div(
            List(
              authorPart,
              datePart,
              catPart,
              commPart
            ).flatten.intersperse(span(" | ")): _*
          ),
          p(article.perex)
        )

        div(BootstrapStyles.row, ArticleListStyles.body)(
          colImg,
          colText
        ).render
      }))
    )
}

case class ArticlesList(articles: Seq[ArticlePreview])
