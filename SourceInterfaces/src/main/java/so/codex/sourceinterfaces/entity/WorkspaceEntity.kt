package so.codex.sourceinterfaces.entity

sealed class WorkspaceEntity(
    open val id: String,
    open val name: String,
    open val description: String? = null,
    open val image: String
)

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