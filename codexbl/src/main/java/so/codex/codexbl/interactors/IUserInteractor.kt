package so.codex.codexbl.interactors

import so.codex.core.entity.SessionData
import so.codex.core.entity.UserToken

/**
 * Interface with declared methods for communication of user data
 * Интерфейс, в котором определены методы необходимые для взаимодействия с пользовательскими данными
 * @author Shiplayer
 */
interface IUserInteractor {
    /**
     * Save session
     * @param session Session representation
     * @return true if session is successful saved else false
     */
    fun saveSession(session: SessionData): Boolean

    /**
     * Update user token
     * @param userToken [UserToken] contain information about access token and refresh token
     */
    fun updateToken(userToken: UserToken)

    /**
     * Get last saved session
     * @return Session representation if it early saved or null
     */
    fun getLastSession(): SessionData?

    /**
     * Clear all information about user
     */
    fun clear()
}