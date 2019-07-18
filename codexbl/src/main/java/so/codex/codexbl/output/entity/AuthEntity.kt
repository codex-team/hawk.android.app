package so.codex.codexbl.output.entity

data class AuthEntity(val email: String, val password: String){
    fun getMutation() = """mutation login(\$email: String!, \$password: String!) {
        |  login(email: \$email, password: \$password) {
        |      accessToken    refreshToken  
        |  }
        |}""".trimMargin()
}