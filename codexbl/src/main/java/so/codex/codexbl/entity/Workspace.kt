package so.codex.codexbl.entity

/**
 * Represent Workspace info
 * @property id Workspace id
 * @property name Workspace name
 * @property description Workspace description
 * @property image Workspace logo image
 * @property projects Workspace projects list
 * @author Shiplayer
 */
data class Workspace(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val image: String = "",
        val projects: List<Project> = listOf()
) {
        fun hasId(): Boolean = id.isNotEmpty()
        fun hasImage(): Boolean = image.isNotEmpty()
}