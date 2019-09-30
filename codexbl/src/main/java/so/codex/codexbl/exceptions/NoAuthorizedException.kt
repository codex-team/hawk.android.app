package so.codex.codexbl.exceptions

/**
 * The Exception denote that current the request require authorization. Hence was sent request with out access token or
 * was expired.
 * @property message Message of Exception
 * @author Shiplayer
 */
data class NoAuthorizedException(override val message: String = "") : Throwable(
        if (message.isEmpty())
            "This action request authorization! You are not authorized!"
        else
            message
)