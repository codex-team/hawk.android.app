package so.codex.codexbl.providers

import android.content.Context
import android.util.Log
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.entity.UserToken

/**
 * For storage data used [android.content.SharedPreferences] for getting and saving user information.
 * Implementation interface [UserTokenDAO].
 * @see android.content.SharedPreferences
 * @author Shiplayer
 */
class UserTokenPreferences(context: Context) : UserTokenDAO {
    /**
     * Private [android.content.SharedPreferences] with access to storage data
     */
    private val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    companion object {
        /**
         * Key for saving token
         */
        const val TOKEN_PREFERENCES_KEY = "token_preferences_key"
        /**
         * Key for saving refresh token
         */
        const val REFRESH_TOKEN_PREFERENCES_KEY = "refresh_token_preferences_key"
        /**
         * Key for saving last email via sign in in application
         */
        const val LAST_SIGNIN_EMAIL_PREFERENCE_KEY = "last_signin_email_preference_key"
    }

    @Synchronized
    override fun getUserToken(): UserToken? {
        val token = preferences.getString(TOKEN_PREFERENCES_KEY, null)
        val refresh = preferences.getString(REFRESH_TOKEN_PREFERENCES_KEY, null)
        return if (token == null || refresh == null) null else UserToken(token, refresh).also {
            Log.i("UserTokenPreference", "get user token = $it")
        }
    }

    @Synchronized
    override fun saveUserToken(userToken: UserToken): Boolean {
        Log.i("UserTokenPreference", "save $userToken")
        preferences.edit()
                .putString(TOKEN_PREFERENCES_KEY, userToken.accessToken)
                .putString(REFRESH_TOKEN_PREFERENCES_KEY, userToken.refreshToken)
                .apply()
        return true
    }

    @Synchronized
    override fun saveSession(sessionData: SessionData): Boolean {
        saveUserToken(sessionData.toUserToken())
        preferences.edit()
                .putString(LAST_SIGNIN_EMAIL_PREFERENCE_KEY, sessionData.email)
                .apply()
        return true
    }

    @Synchronized
    override fun getLastSession(): SessionData? {
        return getUserToken().let {
            val email = preferences.getString(LAST_SIGNIN_EMAIL_PREFERENCE_KEY, null)
            if (email == null || it == null)
                null
            else
                SessionData(email, it.accessToken, it.refreshToken)
        }
    }

    @Synchronized
    override fun clean() {
        preferences.edit()
                .remove(TOKEN_PREFERENCES_KEY)
                .remove(REFRESH_TOKEN_PREFERENCES_KEY)
                .apply()
    }
}