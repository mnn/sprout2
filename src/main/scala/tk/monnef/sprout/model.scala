package tk.monnef.sprout

case class NameUrlPair(name: String, url: String)

case class ArticlePreview(
                           name: String,
                           url: String,
                           urlEncoded: String,
                           perex: String,
                           author: NameUrlPair,
                           date: String,
                           category: NameUrlPair,
                           commentsCount: Option[Int],
                           imageUrl: String,
                           isActuality: Boolean,
                           parsedDate: String,
                           isPr: Boolean
                         )

case class Article(
                    name: String,
                    imageUrl: String,
                    author: NameUrlPair,
                    date: String,
                    perex: String,
                    body: String
                  )

object Model {
  val baseUrl = "http://root.cz"
}