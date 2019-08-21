package so.codex.codexbl.entity

data class Project(
        val id: String,
        val token: String,
        val name: String,
        val description: String,
        val url: String,
        val image: String,
        val events: List<Event>
)