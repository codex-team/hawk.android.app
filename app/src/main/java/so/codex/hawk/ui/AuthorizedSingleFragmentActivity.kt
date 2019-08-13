package so.codex.hawk.ui

import android.os.Bundle
import androidx.loader.app.LoaderManager
import so.codex.codexbl.presenter.AuthorizedPresenter
import so.codex.codexbl.view.IAuthorizedView
import so.codex.hawk.base.BaseSingleFragmentActivity
import so.codex.hawk.base.HawkApplication
import so.codex.hawk.router.ILogoutRouter

abstract class AuthorizedSingleFragmentActivity : BaseSingleFragmentActivity(), ILogoutRouter {
    private val mAuthorizedView: IAuthorizedView = object : IAuthorizedView {
        override fun showErrorMessage(message: String) {

        }

        override fun logout() {
            this@AuthorizedSingleFragmentActivity.logout()
        }

    }

    private val mAuthPresenter = (application as HawkApplication).getAuthPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuthPresenter.checkAuthorization()
    }
}