package so.codex.codexbl.presenter

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.interactors.IUserInteractor
import so.codex.codexbl.view.IAuthorizedView

class AuthorizedPresenter() : BasePresenter<IAuthorizedView>(), KoinComponent {
    private val userInteractor: IUserInteractor by inject()

    override fun onAttach() {
        super.onAttach()
        checkAuthorization()
    }

    fun checkAuthorization() {
        if (!userInteractor.getLastSession().let {
                it != null && it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()
            })
            view?.logout()
    }

    fun clearAndLogout() {
        userInteractor.clear()
        view?.logout()
    }
}