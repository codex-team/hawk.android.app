package so.codex.sourceinterfaces.entity

/**
 * Структура данных, которой можно взаимодействовать между уровнями Интеракторов и
 * Репозиториями/API.
 * @param id Уникальный индификатор проекта
 * @param email Почта пользователя
 * @param name Имя пользователя
 * @param picture Картинка пользователя
 * @author Shiplayer
 */
data class UserEntity(
    val id: String,
    val email: String,
    val name: String,
    val picture: String
)