package so.codex.sourceinterfaces.entity

/**
 * Здесь описаны основные объекты, которыми можно взаимодействовать между уровнями Интеракторов и
 * Репозиториями/API.
 * @author Shiplayer
 */

/**
 * Родительский класс, в котором описаны основные поля для Workspace
 * @param id Уникальный номер Workspace
 * @param name Строка содержащая название Workspace
 * @param description Строка содержащая описание данного Workspace
 * @param image Строка содержащая ссылку на картинку
 */
sealed class WorkspaceEntity(
    open val id: String,
    open val name: String,
    open val description: String? = null,
    open val image: String
)

/**
 * Класс, в котором описано дополнительное поле для проектов
 * @param id Уникальный номер Workspace
 * @param name Строка содержащая название Workspace
 * @param description Строка содержащая описание данного Workspace
 * @param image Строка содержащая ссылку на картинку
 * @param projects Список [ProjectEntity], в которых описана информация о проектах, которые
 * находятся в данных Workspaces
 * @see ProjectEntity
 * @see WorkspaceEntity
 */
data class WorkspaceWithProjectsEntity(
    override val id: String,
    override val name: String,
    override val description: String? = null,
    override val image: String,
    val projects: List<ProjectEntity> = listOf()
) : WorkspaceEntity(
    id,
    name,
    description,
    image
)

/**
 * Класс, в котором описано дополнительное поле для пользователей
 * @param id Уникальный номер Workspace
 * @param name Строка содержащая название Workspace
 * @param description Строка содержащая описание данного Workspace
 * @param image Строка содержащая ссылку на картинку
 * @param users Список пользователей, у которых есть доступ к данному Workspace
 * @see UserEntity
 * @see WorkspaceEntity
 */
data class WorkspaceWithUsersEntity(
    override val id: String,
    override val name: String,
    override val description: String? = null,
    override val image: String,
    val users: List<UserEntity> = listOf()
) : WorkspaceEntity(
    id,
    name,
    description,
    image
)

/**
 * Класс, в котором описано дополнительное поле для пользователей и проектов
 * @param id Уникальный номер Workspace
 * @param name Строка содержащая название Workspace
 * @param description Строка содержащая описание данного Workspace
 * @param image Строка содержащая ссылку на картинку
 * @param projects Список [ProjectEntity], в которых описана информация о проектах, которые
 * находятся в данных Workspaces
 * @param users Список пользователей, у которых есть доступ к данному Workspace
 * @see ProjectEntity
 * @see UserEntity
 * @see WorkspaceEntity
 */
data class FullWorkspaceEntity(
    override val id: String,
    override val name: String,
    override val description: String? = null,
    override val image: String,
    val users: List<UserEntity> = listOf(),
    val projects: List<ProjectEntity> = listOf()
) : WorkspaceEntity(
    id,
    name,
    description,
    image
)