package so.codex.sourceinterfaces.entity

/**
 * Format of task. Used for communication between interactors and repositories/API.
 * @property id Unique identifier of event
 * @property catcherType Hawk catcher name
 * @property payload All information about the event
 * @author Shiplayer
 */
data class EventEntity(
    val id: String,
    val catcherType: String,
    val payload: EventPayloadEntity
)