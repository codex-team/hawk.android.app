package so.codex.sourceinterfaces.entity

/**
 * Структура данных, которой можно взаимодействовать между уровнями Интеракторов и
 * Репозиториями/API.
 * @param id Уникальный индификатор проекта
 * @param token
 * @param name Название проекта
 * @param description Описание проекта
 * @param url Прямая ссылка на проект
 * @param image Ссылка на логотип проекта
 * @param uidAdded Пользователь, который создал данный проект
 * @param events События, которые произошли в проекте
 * @author Shiplayer
 */
data class ProjectEntity(
    val id: String,
    val token: String,
    val name: String,
    val description: String,
    val url: String,
    val image: String,
    val uidAdded: UserEntity? = null,
    val events: List<EventEntity>
)