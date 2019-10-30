package so.codex.sourceinterfaces.entity

/**
 * Entity contain information that needed for updating session. Used only for sending information from domain to
 * Repository/Api layer
 * @property refreshToken Token that require for updating session
 * @author Shiplayer
 */
data class TokenEntity(val refreshToken: String)