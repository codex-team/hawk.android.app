package so.codex.sourceinterfaces.entity

/**
 * Структура данных, которой можно взаимодействовать между уровнями Интеракторов и
 * Репозиториями/API.
 * @param email Почта, по которой пользователь будет зарегистрирован
 * @author Shiplayer
 */
data class SignUpEntity(val email: String)