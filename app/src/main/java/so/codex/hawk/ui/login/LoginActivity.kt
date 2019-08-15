package so.codex.hawk.ui.login

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import so.codex.hawk.BuildConfig
import so.codex.hawk.R
import so.codex.hawk.base.BaseSingleFragmentActivity
import so.codex.hawk.base.InnerSingleFragment

/**
 * Активити, которая отвечает за вход в приложение
 */
class LoginActivity : BaseSingleFragmentActivity() {
    override val containerId: Int
        get() = R.id.frame_container

    /**
     * Инициализация экрана
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        build_name.text = BuildConfig.VERSION_NAME
        replaceFragment(SignInFragment.instance(intent?.extras))
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            for (fragment in supportFragmentManager.fragments) {
                if (fragment is InnerSingleFragment) {
                    val handled = fragment.onBackPressed()
                    if (handled) {
                        return
                    }
                }
            }
            super.onBackPressed()
        }
    }
}