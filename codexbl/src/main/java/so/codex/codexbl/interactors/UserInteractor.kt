package so.codex.codexbl.interactors

import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.providers.UserTokenDAO

/**
 * Interacor for saving and getting information of user
 * @author Shiplayer
 */
class UserInteractor : IUserInteractor, KoinComponent {
    /**
     * Instance that provide access and way to save and get data
     */
    private val userTokenDAO: UserTokenDAO by inject(named("preferences"))

    /**
     * Method for saving session
     * @param session session date representation
     * @return return true if saved session is successful else false
     */
    override fun saveSession(session: SessionData): Boolean {
        Log.i("UserInteractor", "saveSession = $session")
        return userTokenDAO.saveSession(session)
    }

    /**
     * Method for getting last saved session
     * @return session date representation [SessionData] or null, if last session was not found
     */
    override fun getLastSession(): SessionData? {
        return userTokenDAO.getLastSession().also {
            Log.i("UserInteractor", "data = $it")
        }
    }

    /**
     * Clear all information of session
     */
    override fun clear() {
        userTokenDAO.clean()
    }
}