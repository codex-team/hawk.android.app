package so.codex.hawkapi.exceptions

/**
 * An exception is described that the access token has expired and is no longer valid
 * @see BaseHttpException
 * @author Shiplayer
 */
class AccessTokenExpiredException: BaseHttpException("Access token is expired")