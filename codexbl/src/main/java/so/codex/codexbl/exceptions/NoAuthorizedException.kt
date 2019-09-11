package so.codex.codexbl.exceptions

/**
 * Исключение, которое обозначает, что данный запрос требует авторизации. Следовательно был
 * отправлен запрос без токена или он перестал быть валидным
 * @author Shiplayer
 */
data class NoAuthorizedException(override val message: String = "") : Throwable(
        if (message.isEmpty())
            "This action request authorization! You are not authorized!"
        else
            message
)