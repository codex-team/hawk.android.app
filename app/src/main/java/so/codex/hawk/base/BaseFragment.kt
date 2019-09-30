package so.codex.hawk.base

import androidx.fragment.app.Fragment
import so.codex.codexbl.view.base.IBaseView
import so.codex.hawk.router.IBaseRouter

/**
 * Base fragment that contain common ui logic for all fragments
 */
abstract class BaseFragment : Fragment(), IBaseView {
    /**
     * Get router with type [R]. Check if parent fragment or activity implement [R] then return that fragment as [R] else
     * throw exception of [ClassCastException]
     * @return Router with type of [R]
     */
    inline fun <reified R : IBaseRouter> getRouter(): R {
        return when {
            parentFragment is R -> parentFragment as R
            activity is R -> activity as R
            else -> throw ClassCastException()
        }
    }
}