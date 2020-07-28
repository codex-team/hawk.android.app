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
 * @author Shiplayer
 */
open class ProjectEntity(
    open val id: String,
    open val token: String,
    open val name: String,
    open val description: String,
    open val url: String,
    open val image: String,
    open val uidAdded: UserEntity? = null
)

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
 */
data class FullProjectEntity(
    override val id: String,
    override val token: String,
    override val name: String,
    override val description: String,
    override val url: String,
    override val image: String,
    override val uidAdded: UserEntity? = null,
    val events: List<EventEntity>
) : ProjectEntity(
    id,
    token,
    name,
    description,
    url,
    image,
    uidAdded
)

/**
 * Data class contain information about project and events that occurred
 * @property id Unique identifier of project
 * @property token
 * @property name Name of project
 * @property description Description of project
 * @property url Link on project
 * @property image URL of image
 * @property uidAdded Owner of project
 */
data class OnlyProjectEntity(
    override val id: String,
    override val token: String,
    override val name: String,
    override val description: String,
    override val url: String,
    override val image: String,
    override val uidAdded: UserEntity? = null
) : ProjectEntity(
    id,
    token,
    name,
    description,
    url,
    image,
    uidAdded
)