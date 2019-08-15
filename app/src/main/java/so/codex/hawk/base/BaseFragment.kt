package so.codex.hawk.base

import androidx.fragment.app.Fragment
import so.codex.codexbl.view.base.IBaseView
import so.codex.hawk.router.IBaseRouter

/**
 * Родительский фрагмент, который описывает общую логику для всех фрагментов
 */
abstract class BaseFragment : Fragment(), IBaseView {
    /*abstract val presenters: List<BasePresenter<IBaseView>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenters.forEach {
            it.attached(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenters.forEach {
            it.detached()
        }
    }*/

    inline fun <reified R : IBaseRouter> getRouter(): R {
        return when {
            parentFragment is R -> parentFragment as R
            activity is R -> activity as R
            else -> throw ClassCastException()
        }
    }
}