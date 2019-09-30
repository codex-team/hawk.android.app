package so.codex.codexbl.entity

/**
 * Session data representation
 * @property email Email of user
 * @property accessToken Session access token
 * @property refreshToken Session refresh token
 * @author Shiplayer
 */
data class SessionData(
        val email: String,
        val accessToken: String,
        val refreshToken: String) {
    fun toUserToken() = UserToken(accessToken, refreshToken)
}