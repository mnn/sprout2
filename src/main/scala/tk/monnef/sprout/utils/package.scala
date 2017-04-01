package tk.monnef.sprout

import scalacss.internal.StyleA

package object utils {

  implicit class ListPimps[A](xs: List[A]) {
    def intersperse(padding: A): List[A] = {
      xs.foldLeft(List[A]()) { case (acc, item) =>
        if (acc.isEmpty) List(item)
        else acc ++ List(padding) ++ List(item)
      }
    }
  }

  implicit class StringPimps(x: String) {
    def toNonEmptyOpt: Option[String] = if (x.isEmpty) None else Some(x)
  }

  implicit class StyleAPimps(x: StyleA) {
    def cond(c: Boolean): List[StyleA] = if (c) List(x) else List()
  }

}
