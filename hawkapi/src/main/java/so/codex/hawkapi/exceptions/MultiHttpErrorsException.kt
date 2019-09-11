package so.codex.hawkapi.exceptions

/**
 * Возникает, когда во время выполнения HTTP запроса происходит больше чем одна ошибка.
 * @param message сообщение, в которое содержит какое то обяснение, причина возникновения
 * @param errors Список сообщений основных ошибок, которые произошли
 * @see BaseHttpException
 * @author Shiplayer
 */
class MultiHttpErrorsException(message: String, val errors: List<String>) : BaseHttpException(message)