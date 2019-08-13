package so.codex.codexbl.entity

data class SessionData(val email: String, val accessToken: String, val refreshToken: String){
    fun toUserToken() = UserToken(accessToken, refreshToken)
}