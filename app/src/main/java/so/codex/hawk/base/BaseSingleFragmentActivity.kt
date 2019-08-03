package so.codex.hawk.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import so.codex.hawk.router.IBaseRouter

abstract class BaseSingleFragmentActivity : FragmentActivity(), IBaseRouter {
    protected abstract val containerId: Int

    companion object {
        private const val SAVED_FRAGMENT_NAME_KEY = "saved_fragment_name_key"
    }

    override fun replaceFragment(fragment: Fragment) {
        val oldFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
        oldFragment?.arguments = fragment.arguments
        supportFragmentManager
                .beginTransaction()
                .replace(containerId, oldFragment ?: fragment, fragment::class.java.simpleName)
                .commit()
    }

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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val showedFragment = supportFragmentManager.findFragmentById(containerId)
        val tag = if (showedFragment != null) showedFragment::class.java.simpleName else ""
        outState?.putString(SAVED_FRAGMENT_NAME_KEY, tag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            val tag = it.getString(SAVED_FRAGMENT_NAME_KEY)
            if(!tag.isNullOrEmpty())
                replaceByFragmentName(tag)
        }
    }
}