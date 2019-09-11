package so.codex.codexbl.entity

/**
 * Структура данных используется для получения данных из базы данных
 * @author Shiplayer
 */
data class UserToken(val accessToken: String, val refreshToken: String)