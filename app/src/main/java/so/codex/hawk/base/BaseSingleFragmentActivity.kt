package so.codex.hawk.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import so.codex.hawk.router.IBaseRouter

abstract class BaseSingleFragmentActivity : FragmentActivity(), IBaseRouter {
    protected abstract val containerId: Int

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
                .replace(containerId, oldFragment ?: fragment, tag ?: fragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
    }
}