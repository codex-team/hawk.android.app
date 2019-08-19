package so.codex.sourceinterfaces.entity

data class EventEntity(
    val id: String,
    val catcherType: String,
    val payload: EventPayloadEntity
)