package so.codex.hawk.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import so.codex.hawk.router.IBaseRouter

/**
 * Abstract class for activity that should contain only one container for fragment. Add feature for retain fragment
 * after changing system settings.
 */
abstract class BaseSingleFragmentActivity : FragmentActivity(), IBaseRouter {
    /**
     * Unique identification container that can change fragment on layout
     */
    protected abstract val containerId: Int

    companion object {
        /**
         * Key for saving last opened fragment [containerId] for restoring state
         */
        private const val SAVED_FRAGMENT_NAME_KEY = "saved_fragment_name_key"
    }

    /**
     * Find fragment in stake, if it exist else then insert it in [containerId] with out adding in stack
     * @param fragment instance of fragment that need to replace.
     */
    override fun replaceFragment(fragment: Fragment) {
        val oldFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
        oldFragment?.arguments = fragment.arguments
        supportFragmentManager
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
        val oldFragment = supportFragmentManager.findFragmentByTag(tag
                                                                           ?: fragment::class.java.simpleName)
        oldFragment?.arguments = fragment.arguments
        supportFragmentManager
                .beginTransaction()
                .replace(containerId, oldFragment ?: fragment, tag
                        ?: fragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
    }

    /**
     * Find fragment in stack by tag and replace fragment in [containerId] on it
     * @param tag Unique name of fragment by it we can find in stack
     */
    private fun replaceByFragmentName(tag: String) {
        val oldFragment = supportFragmentManager.findFragmentByTag(tag)
        //oldFragment?.arguments = fragment.arguments
        if (oldFragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(containerId, oldFragment, tag)
                    .commit()
        }
    }

    /**
     * Save last showed fragment in [containerId] by use shared preference
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val showedFragment = supportFragmentManager.findFragmentById(containerId)
        val tag = if (showedFragment != null) showedFragment::class.java.simpleName else ""
        outState?.putString(SAVED_FRAGMENT_NAME_KEY, tag)
    }

    /**
     * Restore last showed fragment in [containerId] by use shared preference
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            val tag = it.getString(SAVED_FRAGMENT_NAME_KEY)
            if(!tag.isNullOrEmpty())
                replaceByFragmentName(tag)
        }
    }
}