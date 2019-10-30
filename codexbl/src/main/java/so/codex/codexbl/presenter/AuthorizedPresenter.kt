package so.codex.codexbl.presenter

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.interactors.IUserInteractor
import so.codex.codexbl.view.IAuthorizedView

/**
 * Presentor for validation of user, check if user is authorized or not, then clear all data,
 * session and logout
 * @author Shiplayer
 */
class AuthorizedPresenter : BasePresenter<IAuthorizedView>(), KoinComponent {
    /**
     * Interactor for checking user authorization state and logout
     */
    private val userInteractor: IUserInteractor by inject()

    override fun onAttach() {
        super.onAttach()
        checkAuthorization()
    }

    /**
     * Check if user authorized.
     */
    fun checkAuthorization() {
        if (!userInteractor.getLastSession().let {
                it != null && it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()
            })
            view?.logout()
    }

    /**
     * Clear data and logout
     */
    fun clearAndLogout() {
        userInteractor.clear()
        view?.logout()
    }
}