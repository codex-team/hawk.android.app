package so.codex.codexbl.exceptions

data class NoAuthorizedException(override val message: String = "") : Throwable(
        if (message.isEmpty())
            "This action request authorization! You are not authorized!"
        else
            message
)