package so.codex.sourceinterfaces.entity

/**
 * Структура данных, которой можно взаимодействовать между уровнями Интеракторов и
 * Репозиториями/API.
 * @param refreshToken Токен, с помощью которого можно будет обновить сессию и токен
 * @author Shiplayer
 */
data class TokenEntity(val refreshToken: String)