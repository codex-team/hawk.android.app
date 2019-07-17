package so.codex.hawk.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.view.base.IBaseView

/**
 * Родительский фрагмент, который описывает общую логику для всех фрагментов
 */
abstract class BaseFragment : Fragment(), IBaseView {
    abstract val presenters: List<BasePresenter<IBaseView>>

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
    }
}