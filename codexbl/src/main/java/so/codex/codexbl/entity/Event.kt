package so.codex.codexbl.entity

data class Event(
        val id: String,
        val catcherType: String,
        val payload: Payload
)