package so.codex.sourceinterfaces.entity

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