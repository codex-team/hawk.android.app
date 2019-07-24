package so.codex.sourceinterfaces.entity

data class SignUpEntity(val email: String) {
    fun getMutation() = """mutation signUp(${"$"}email: String!) {
        |   signUp(email: ${"$"}email)
        |}""".trimMargin()
}