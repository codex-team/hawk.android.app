package so.codex.codexbl.interactors

import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.providers.UserTokenDAO

class UserInteractor : IUserInteractor, KoinComponent {
    private val userTokenDAO: UserTokenDAO by inject(named("preferences"))

    override fun saveSession(session: SessionData): Boolean {
        Log.i("UserInteractor", "saveSession = $session")
        return userTokenDAO.saveSession(session)
    }

    override fun getLastSession(): SessionData? {
        return userTokenDAO.getLastSession().also {
            Log.i("UserInteractor", "data = $it")
        }
    }

    override fun clear() {
        userTokenDAO.clean()
    }
}