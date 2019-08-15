package so.codex.codexbl.providers

import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.entity.UserToken

interface UserTokenDAO {
    fun getUserToken(): UserToken?

    fun saveUserToken(userToken: UserToken): Boolean

    fun saveSession(sessionData: SessionData): Boolean

    fun getLastSession(): SessionData?

    fun clean()

}