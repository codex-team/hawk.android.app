package so.codex.hawk.base

import android.app.Application
import so.codex.codexbl.main.StartKoinComponent
import so.codex.codexbl.presenter.AuthorizedPresenter

class HawkApplication : Application() {
    private val authorizedPresenter by lazy {
        AuthorizedPresenter()
    }

    override fun onCreate() {
        super.onCreate()
        StartKoinComponent.start(this)
    }

    fun getAuthPresenter() = authorizedPresenter
}