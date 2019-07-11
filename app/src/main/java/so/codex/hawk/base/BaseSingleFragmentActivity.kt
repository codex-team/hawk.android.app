package so.codex.hawk.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import so.codex.hawk.router.IBaseRouter

abstract class BaseSingleFragmentActivity : FragmentActivity(), IBaseRouter {
    protected abstract val containerId: Int

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    override fun replaceAndAdd(fragment: Fragment, tag: String?) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}