package so.codex.sourceinterfaces.entity

import java.util.Date

/**
 * Information about event.
 * @property title Event title
 * @property timestamp Occurrence time
 * @author Shiplayer
 */
data class EventPayloadEntity(
    val title: String,
    val timestamp: Date
)