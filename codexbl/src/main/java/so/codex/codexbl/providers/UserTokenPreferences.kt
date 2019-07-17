package so.codex.codexbl.providers

import android.content.Context
import so.codex.codexbl.entity.UserToken

class UserTokenPreferences(context: Context) : UserTokenDAO {
    private val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    companion object {
        const val TOKEN_PREFERENCES_KEY = "token_preferences_key"
        const val REFRESH_TOKEN_PREFERENCES_KEY = "refresh_token_preferences_key"
    }

    override fun getUserToken(): UserToken? {
        val token = preferences.getString(TOKEN_PREFERENCES_KEY, null)
        val refresh = preferences.getString(REFRESH_TOKEN_PREFERENCES_KEY, null)
        return if (token == null || refresh == null) null else UserToken(token, refresh)
    }

    override fun saveUserToken(userToken: UserToken): Boolean {
        preferences.edit()
                .putString(TOKEN_PREFERENCES_KEY, userToken.accessToken)
                .putString(REFRESH_TOKEN_PREFERENCES_KEY, userToken.refreshToken)
                .apply()
        return true
    }

    override fun clean() {
        preferences.edit()
                .remove(TOKEN_PREFERENCES_KEY)
                .remove(REFRESH_TOKEN_PREFERENCES_KEY)
                .apply()
    }
}