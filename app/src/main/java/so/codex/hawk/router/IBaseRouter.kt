package so.codex.hawk.router

import androidx.fragment.app.Fragment

interface IBaseRouter {
    fun replaceFragment(fragment: Fragment)

    fun replaceAndAdd(fragment: Fragment, tag: String? = null)
}