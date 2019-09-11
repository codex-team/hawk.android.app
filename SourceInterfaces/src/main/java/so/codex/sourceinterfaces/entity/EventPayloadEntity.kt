package so.codex.sourceinterfaces.entity

import java.util.*

/**
 * Структура данных, которой можно взаимодействовать между уровнями Интеракторов и
 * Репозиториями/API.
 * @param title
 * @param timestamp Время, когда произошло данное событие
 * @author Shiplayer
 */
data class EventPayloadEntity(
    val title: String,
    val timestamp: Date
)