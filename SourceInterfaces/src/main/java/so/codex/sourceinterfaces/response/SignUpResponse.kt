package so.codex.sourceinterfaces.response

/**
 * Representation of response from http request on registration. Used for handling response after sending http
 * request and only in api layer.
 * @property signUp If user successfully registered
 * @author Shiplayer
 */
data class SignUpResponse(val signUp: Boolean)