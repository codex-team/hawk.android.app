package so.codex.codexsource.exceptions

class MultiHttpErrorsException(message: String, val errors: List<String>) : BaseHttpException(message)