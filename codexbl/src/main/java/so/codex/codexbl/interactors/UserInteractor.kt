package so.codex.codexbl.interactors

import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.providers.UserTokenDAO

class UserInteractor : IUserInteractor, KoinComponent {
    private val userTokenDAO: UserTokenDAO by inject(named("preferences"))

    override fun saveSession(session: SessionData): Boolean {
        userTokenDAO.saveUserToken(session.toUserToken())
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}