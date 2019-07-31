package so.codex.hawkapi.exceptions

class MultiHttpErrorsException(message: String, val errors: List<String>) : BaseHttpException(message)