package so.codex.sourceinterfaces.entity

/**
 * Структура данных, которой можно взаимодействовать между уровнями Интеракторов и
 * Репозиториями/API.
 * @param id Уникальный номер, произошедшего события
 * @param catcherType
 * @param payload
 * @author Shiplayer
 */
data class EventEntity(
    val id: String,
    val catcherType: String,
    val payload: EventPayloadEntity
)