package so.codex.codexbl.entity

/**
 * User Authorization representation
 * @property email Email of user
 * @property password Password of user
 * @author Shiplayer
 */
data class UserAuth(
        val email: String,
        val password: String
)