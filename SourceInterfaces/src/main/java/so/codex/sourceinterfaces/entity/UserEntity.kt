package so.codex.sourceinterfaces.entity

/**
 * Data class have information about user. Used for communication between interactors and
 * repositories/API.
 * @property id Unique identifier for user
 * @property email Email of user
 * @property name Name of user
 * @property picture Picture of user
 * @author Shiplayer
 */
data class UserEntity(
    val id: String,
    val email: String,
    val name: String,
    val picture: String
)