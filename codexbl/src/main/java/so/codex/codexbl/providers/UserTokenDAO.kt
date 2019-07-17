package so.codex.codexbl.providers

import so.codex.codexbl.entity.UserToken

interface UserTokenDAO {
    fun getUserToken(): UserToken?

    fun saveUserToken(userToken: UserToken): Boolean

    fun clean()

}