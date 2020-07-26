package so.codex.hawkapi

import so.codex.hawkapi.fragment.EventsList
import so.codex.hawkapi.fragment.ProjectsList
import so.codex.sourceinterfaces.entity.EventEntity
import so.codex.sourceinterfaces.entity.EventPayloadEntity
import so.codex.sourceinterfaces.entity.FullProjectEntity
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.OnlyProjectEntity
import so.codex.sourceinterfaces.entity.OnlyWorkspaceEntity
import so.codex.sourceinterfaces.entity.ProjectEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse
import java.util.Date

/**
 * Convert from [GetWorkspacesQuery.Workspace] to [FullWorkspaceEntity] that wrapped to response instance
 * @return [WorkspaceResponse] with type of [FullWorkspaceEntity]
 */
internal fun GetWorkspacesQuery.Workspace.toFullWorkspaceResponse(): WorkspaceResponse<FullWorkspaceEntity> {
    return WorkspaceResponse(
        FullWorkspaceEntity(
            this.id,
            this.name ?: "",
            "",
            this.image ?: "",
            listOf(),
            this.projects?.map {
                it.fragments.toProjectEntity()
            } ?: listOf()
        )
    )
}

/**
 * Convert from [GetWorkspacesQuery.Workspace] to [FullWorkspaceEntity] that wrapped to response instance
 * @return [WorkspaceResponse] with type of [FullWorkspaceEntity]
 */
internal fun GetWorkspacesWithProjectsQuery.Workspace.toWorkspacesWithProjectsResponse(): WorkspaceResponse<WorkspaceWithProjectsEntity> {
    return WorkspaceResponse(
        WorkspaceWithProjectsEntity(
            this.id,
            this.name ?: "",
            "",
            this.image ?: "",
            this.projects?.map {
                it.fragments.toOnlyProjectEntity()
            } ?: listOf()
        )
    )
}

/**
 * Convert from [GetWorkspacesQuery.Workspace] to [FullWorkspaceEntity] that wrapped to response instance
 * @return [WorkspaceResponse] with type of [FullWorkspaceEntity]
 */
internal fun GetOnlyWorkspaceQuery.Workspace.toOnlyWorkspaceResponse(): WorkspaceResponse<OnlyWorkspaceEntity> {
    return WorkspaceResponse(
        OnlyWorkspaceEntity(
            this.id,
            this.name ?: "",
            "",
            this.image ?: ""
        )
    )
}

/**
 * Convert from [GetWorkspacesQuery.Project.Fragments] to [ProjectEntity]
 */
private fun GetWorkspacesQuery.Project.Fragments.toProjectEntity(): FullProjectEntity {
    return FullProjectEntity(
        this.projectsList.id,
        "",
        this.projectsList.name,
        projectsList.description ?: "",
        "",
        this.projectsList.image ?: "",
        null,
        this.projectsList.events?.map {
            it.fragments.toEventEntity()
        } ?: listOf()
    )
}

/**
 * Convert from [GetWorkspacesQuery.Project.Fragments] to [ProjectEntity]
 */
private fun GetWorkspacesWithProjectsQuery.Project.Fragments.toOnlyProjectEntity(): OnlyProjectEntity {
    return OnlyProjectEntity(
        this.onlyProjectsList.id,
        "",
        this.onlyProjectsList.name,
        onlyProjectsList.description ?: "",
        "",
        this.onlyProjectsList.image ?: "",
        null
    )
}

/**
 * Convert from [ProjectsList.Event.Fragments] to [ProjectEntity]
 */
private fun ProjectsList.Event.Fragments.toEventEntity(): EventEntity {
    return EventEntity(
        this.eventsList.id,
        "",
        this.eventsList.payload.toEventPayloadEntity()
    )
}

/**
 * Convert from [EventsList.Payload] to [EventPayloadEntity]
 */
private fun EventsList.Payload.toEventPayloadEntity(): EventPayloadEntity {
    return EventPayloadEntity(
        this.title,
        Date((this.timestamp * 1000).toLong())
    )
}