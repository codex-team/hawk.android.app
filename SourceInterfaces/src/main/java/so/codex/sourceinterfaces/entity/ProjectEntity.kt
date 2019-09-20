package so.codex.sourceinterfaces.entity

/**
 * Data class contain information about project and events that occurred
 * @property id Unique identifier of project
 * @property token
 * @property name Name of project
 * @property description Description of project
 * @property url Link on project
 * @property image URL of image
 * @property uidAdded Owner of project
 * @property events Event that occurred in the project
 * @author Shiplayer
 */
data class ProjectEntity(
    val id: String,
    val token: String,
    val name: String,
    val description: String,
    val url: String,
    val image: String,
    val uidAdded: UserEntity? = null,
    val events: List<EventEntity>
)