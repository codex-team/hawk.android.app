package so.codex.hawkapi.exceptions

import so.codex.core.entity.UserToken

/**
 * An exception is described that the access token has expired and is no longer valid
 * @see BaseHttpException
 * @author Shiplayer
 */
class AccessTokenExpiredException(val token: UserToken? = null) :
    BaseHttpException("Access token is expired")