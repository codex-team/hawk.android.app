package so.codex.core.entity

/**
 * User token representation
 * @property accessToken User's access token
 * @property refreshToken User's refresh token for getting new token pair
 * @author Shiplayer
 */
data class UserToken(val accessToken: String, val refreshToken: String) {
    companion object {
        val EMPTY_TOKEN = UserToken("", "")
    }

}