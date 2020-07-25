package so.codex.codexbl.entity

/**
 * Represent Profile info
 * @property id User id
 * @property email User email
 * @property name User name
 * @property picture User icon
 * @author YorkIsMine
 */

data class Profile(
    val id: String,
    val email: String,
    val name: String,
    val picture: String
)