package so.codex.sourceinterfaces.response

/**
 * Use only in api layer for handling http response
 * @property accessToken Token for access to all api request
 * @property refreshToken Token for updating session
 * @author Shiplayer
 */
data class TokenResponse(val accessToken: String, val refreshToken: String)