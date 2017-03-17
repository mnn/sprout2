package tk.monnef.sprout

import io.udash.rest._
import scala.concurrent.Future

@REST trait StumpRest {
  @GET def articles(): Future[Seq[ArticlePreview]]

  @GET def article(@URLPart url: String): Future[Article]
}