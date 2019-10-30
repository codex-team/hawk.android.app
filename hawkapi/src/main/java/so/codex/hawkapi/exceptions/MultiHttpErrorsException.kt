package so.codex.hawkapi.exceptions

/**
 * If on sending request, we getting in message more that one error that occurred on the server
 * @param message Contain a description of the error or errors that occurred on the server
 * @param errors List of errors
 * @see BaseHttpException
 * @author Shiplayer
 */
class MultiHttpErrorsException(message: String, val errors: List<String>) : BaseHttpException(message)