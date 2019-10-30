package so.codex.codexbl.entity

import java.util.Date

/**
 * Type representing Event payload
 * @property title Event title
 * @property timestamp Event timestamp
 */
data class Payload(
        val title: String,
        val timestamp: Date
)