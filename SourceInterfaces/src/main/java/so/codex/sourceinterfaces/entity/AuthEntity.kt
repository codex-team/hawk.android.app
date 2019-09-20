package so.codex.sourceinterfaces.entity

/**
 * Data class for authorization user. Used for communication between interactors and
 * repositories/API.
 * @property email Email for authorization
 * @property password Password for authorization
 * @author Shiplayer
 */
data class AuthEntity(val email: String, val password: String)