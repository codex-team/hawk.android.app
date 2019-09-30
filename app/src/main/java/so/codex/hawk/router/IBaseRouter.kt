package so.codex.hawk.router

import androidx.fragment.app.Fragment

/**
 * Common interface with declared method for base activity
 */
interface IBaseRouter {
    fun replaceFragment(fragment: Fragment)

    fun replaceAndAdd(fragment: Fragment, tag: String? = null)
}