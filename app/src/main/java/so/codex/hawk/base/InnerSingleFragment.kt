package so.codex.hawk.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import so.codex.hawk.router.IBaseRouter

/**
 * Abstract class for showing inner changed container with fragment with saving state of current fragment, that last
 * showed.
 */
abstract class InnerSingleFragment : BaseFragment(), IBaseRouter {
    /**
     * Unique identification container that can change fragment on layout
     */
    abstract val containerId: Int

    companion object {
        /**
         * Key for saving last opened fragment [containerId] for restoring state
         */
        private const val SAVED_INNER_FRAGMENT_NAME_KEY = "saved_inner_fragment_name_key"
    }

    /**
     * Find fragment in stake, if it exist else then insert it in [containerId] with out adding in stack
     * @param fragment instance of fragment that need to replace.
     */
    override fun replaceFragment(fragment: Fragment) {
        val oldFragment = childFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
        oldFragment?.arguments = fragment.arguments
        childFragmentManager
                .beginTransaction()
                .replace(containerId, oldFragment ?: fragment, fragment::class.java.simpleName)
                .commit()
    }

    /**
     * Find fragment in stake, if it exist else then insert it in [containerId] and adding in stack for reused it in
     * future or retain state of fragment
     * @param fragment instance of fragment that need to replace.
     * @param tag Unique name of fragment by it we can find in stack
     */
    override fun replaceAndAdd(fragment: Fragment, tag: String?) {
        val oldFragment = childFragmentManager.findFragmentByTag(
                tag
                        ?: fragment::class.java.simpleName
        )
        oldFragment?.arguments = fragment.arguments
        childFragmentManager
                .beginTransaction()
                .replace(
                        containerId, oldFragment ?: fragment, tag
                        ?: fragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
    }

    /**
     * Find fragment in stack by tag and replace fragment in [containerId] on it
     * @param tag Unique name of fragment by it we can find in stack
     */
    private fun replaceByFragmentName(tag: String) {
        val oldFragment = childFragmentManager.findFragmentByTag(tag)
        //oldFragment?.arguments = fragment.arguments
        if (oldFragment != null) {
            childFragmentManager
                    .beginTransaction()
                    .replace(containerId, oldFragment, tag)
                    .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val showedFragment = childFragmentManager.findFragmentById(containerId)
        val tag = if (showedFragment != null) showedFragment::class.java.simpleName else ""
        outState.putString(SAVED_INNER_FRAGMENT_NAME_KEY, tag)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            val tag = it.getString(SAVED_INNER_FRAGMENT_NAME_KEY)
            if (!tag.isNullOrEmpty())
                replaceByFragmentName(tag)
        }
    }

    /**
     * Check if in stack of fragment not have any fragment then invoke back press else invoke of every inner fragment
     * event of back pressed
     * @return return true, if all fragmented deleted from stack and false if we have any fragment in stack that not
     * invoked event of back pressed
     */
    fun onBackPressed(): Boolean {
        return if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            true
        } else {
            for (fragment in childFragmentManager.fragments) {
                if (fragment is InnerSingleFragment) {
                    val handled = fragment.onBackPressed()
                    if (handled) {
                        return true
                    }
                }
            }
            false
        }
    }
}