package so.codex.sourceinterfaces.entity

/**
 * Entity for sending information about user for registration from domain layer to api.
 * @property email Email address for registration of new user
 * @author Shiplayer
 */
data class SignUpEntity(val email: String)