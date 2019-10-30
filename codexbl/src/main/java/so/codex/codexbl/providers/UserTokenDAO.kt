package so.codex.codexbl.providers

import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.entity.UserToken

/**
 * Interface that declared common methods for communication with storage, that contain common
 * information of user with session data
 */
interface UserTokenDAO {
    /**
     * Get user token
     * @return User token representation like as [UserToken], if managed to extract from storage
     * information of access token and refresh token else null
     */
    fun getUserToken(): UserToken?

    /**
     * Save token in storage
     * @param userToken User token representation
     * @return Return true if user token successful saved in storage else false
     */
    fun saveUserToken(userToken: UserToken): Boolean

    /**
     * Save session in storage
     * @param sessionData Session data representation
     * @return Return true if session data successful saved in storage else false
     */
    fun saveSession(sessionData: SessionData): Boolean

    /**
     * Get last saved session
     * @return session date representation [SessionData] or null, if last session was not found
     */
    fun getLastSession(): SessionData?

    /**
     * Clear all information of session
     */
    fun clean()

}