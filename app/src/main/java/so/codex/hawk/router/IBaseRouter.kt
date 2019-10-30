package so.codex.hawk.router

import androidx.fragment.app.Fragment

/**
 * Common interface with declared method for base activity
 */
interface IBaseRouter {

    /**
     * Replace fragment
     * @param fragment instance of fragment that need to replace.
     */
    fun replaceFragment(fragment: Fragment)

    /**
     * Replace and add fragment to back stack
     * @param fragment instance of fragment that need to replace.
     * @param tag Unique name of fragment by it we can find in stack
     */
    fun replaceAndAdd(fragment: Fragment, tag: String? = null)
}