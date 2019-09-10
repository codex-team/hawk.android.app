package so.codex.hawk.router

import androidx.fragment.app.Fragment

/**
 * Основное интерфейс, в котором определены методы для основных активити
 */
interface IBaseRouter {
    fun replaceFragment(fragment: Fragment)

    fun replaceAndAdd(fragment: Fragment, tag: String? = null)
}