package so.codex.hawk.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import so.codex.codexbl.presenter.AuthorizedPresenter
import so.codex.codexbl.view.IAuthorizedView
import so.codex.hawk.base.BaseSingleFragmentActivity
import so.codex.hawk.router.ILogoutRouter
import so.codex.hawk.ui.login.LoginActivity

abstract class AuthorizedSingleFragmentActivity : BaseSingleFragmentActivity(), ILogoutRouter {
    private val mAuthorizedView: IAuthorizedView = object : IAuthorizedView {
        override fun showErrorMessage(message: String) {

        }

        override fun logout() {
            this@AuthorizedSingleFragmentActivity.logout()
        }

    }

    private val mAuthPresenter = AuthorizedPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuthPresenter.attached(mAuthorizedView)
    }

    override fun onStart() {
        super.onStart()
        if (mAuthPresenter.view == null) {
            mAuthPresenter.attached(mAuthorizedView)
        }
    }

    override fun onStop() {
        super.onStop()
        mAuthPresenter.detached()
    }

    override fun logout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask()
        } else {
            finishAffinity()
        }
        startActivity(Intent(this, LoginActivity::class.java))
    }
}