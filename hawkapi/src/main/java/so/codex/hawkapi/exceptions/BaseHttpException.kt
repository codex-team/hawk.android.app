package so.codex.hawkapi.exceptions

/**
 * Main exception that responsible for errors in message
 * Наследует [Exception]
 * @author Shiplayer
 */
open class BaseHttpException(message: String? = "") : Exception(message)