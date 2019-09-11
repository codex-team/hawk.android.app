package so.codex.codexbl.entity

/**
 * Структура данных, в которой хранится информация о сессии
 * @author Shiplayer
 */
data class SessionData(val email: String, val accessToken: String, val refreshToken: String){
    fun toUserToken() = UserToken(accessToken, refreshToken)
}