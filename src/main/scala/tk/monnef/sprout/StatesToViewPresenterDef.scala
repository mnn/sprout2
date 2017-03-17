package tk.monnef.sprout

import io.udash._
import tk.monnef.sprout.views._

class StatesToViewPresenterDef extends ViewPresenterRegistry[RoutingState] {
  def matchStateToResolver(state: RoutingState): ViewPresenter[_ <: RoutingState] = state match {
    case RootState => RootViewPresenter
    case IndexState => IndexViewPresenter
    case OldIndexState => OldIndexViewPresenter
    case BindingDemoState(urlArg) => BindingDemoViewPresenter(urlArg)
    case DemoStylesState => DemoStylesViewPresenter
    case ArticleState(urlArg) => ArticleViewPresenter(urlArg)
    case _ => ErrorViewPresenter
  }
}
