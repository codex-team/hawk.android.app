package so.codex.hawkapi.exceptions

/**
 * Основной класс, который обозначает, что произошла какая то ошибка во время выполнения HTTP зароса.
 * Наследует [Exception]
 * @author Shiplayer
 */
open class BaseHttpException(message: String? = "") : Exception(message)