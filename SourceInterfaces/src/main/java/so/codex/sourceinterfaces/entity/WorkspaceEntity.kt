package so.codex.sourceinterfaces.entity

/**
 * In this file defined classes for different types of Workspace. All classes used for communication between domain and
 * repository/api layer.
 * @author Shiplayer
 */

/**
 * Main class with declared common properties for Workspace
 * @property id Unique identifier of Workspace
 * @property name Name of Workspace
 * @property description Common information about Workspace
 * @property image Link of image
 */
sealed class WorkspaceEntity(
    open val id: String,
    open val name: String,
    open val description: String? = null,
    open val image: String
)

/**
 * Workspace with additional property for list of projects
 * @property id Unique identifier of Workspace
 * @property name Name of Workspace
 * @property description Common information about Workspace
 * @property image Link of image
 * @property projects List of [ProjectEntity], with common information about projects with events
 * @see ProjectEntity
 * @see WorkspaceEntity
 */
data class WorkspaceWithProjectsEntity(
    override val id: String,
    override val name: String,
    override val description: String? = null,
    override val image: String,
    val projects: List<ProjectEntity> = listOf()
) : WorkspaceEntity(
    id,
    name,
    description,
    image
)

/**
 * Workspace with property of allowed users
 * @property id Unique identifier of Workspace
 * @property name Name of Workspace
 * @property description Common information about Workspace
 * @property image Link of image
 * @property users List of user that have access to Workspace
 * @see UserEntity
 * @see WorkspaceEntity
 */
data class WorkspaceWithUsersEntity(
    override val id: String,
    override val name: String,
    override val description: String? = null,
    override val image: String,
    val users: List<UserEntity> = listOf()
) : WorkspaceEntity(
    id,
    name,
    description,
    image
)

/**
 * Workspace that contains all information with additional fields
 * @property id Unique identifier of Workspace
 * @property name Name of Workspace
 * @property description Common information about Workspace
 * @property image Link of image
 * @property projects List of [ProjectEntity], with common information about projects with events
 * @property users List of user that have access to Workspace
 * @see ProjectEntity
 * @see UserEntity
 * @see WorkspaceEntity
 */
data class FullWorkspaceEntity(
    override val id: String,
    override val name: String,
    override val description: String? = null,
    override val image: String,
    val users: List<UserEntity> = listOf(),
    val projects: List<ProjectEntity> = listOf()
) : WorkspaceEntity(
    id,
    name,
    description,
    image
)