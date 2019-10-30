package so.codex.sourceinterfaces.response

/**
 * Contains information after sending request to login in application. Used for handling response after sending http
 * request and only in api layer.
 * @property login Representation of access and refresh token
 * @author Shiplayer
 */
data class LoginResponse(val login: TokenResponse)