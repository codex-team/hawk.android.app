package so.codex.sourceinterfaces.entity

/**
 * Структура данных, которой можно взаимодействовать между уровнями Интеракторов и
 * Репозиториями/API.
 * @param email Почта, которая указыват пользователь для входа
 * @param password Пароль, которая указыват пользователь для входа
 * @author Shiplayer
 */
data class AuthEntity(val email: String, val password: String)