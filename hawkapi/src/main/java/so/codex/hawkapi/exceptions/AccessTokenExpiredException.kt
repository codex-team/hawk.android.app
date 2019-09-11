package so.codex.hawkapi.exceptions

/**
 * Класс ялвяется [Exception], который возникает в случае, если у токена истек срок валидности
 * @see BaseHttpException
 * @author Shiplayer
 */
class AccessTokenExpiredException: BaseHttpException("Access token is expired")