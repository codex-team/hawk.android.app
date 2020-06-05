package so.codex.hawkapi

import so.codex.hawkapi.fragment.EventsList
import so.codex.hawkapi.fragment.ProjectsList
import so.codex.sourceinterfaces.entity.*
import so.codex.sourceinterfaces.response.WorkspaceResponse
import java.util.*

/**
 * Convert from [GetWorkspacesQuery.Workspace] to [FullWorkspaceEntity] that wrapped to response instance
 * @return [WorkspaceResponse] with type of [FullWorkspaceEntity]
 */
internal fun GetFullWorkspacesQuery.Workspace.toFullWorkspaceResponse(): WorkspaceResponse<FullWorkspaceEntity> {
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
private fun GetFullWorkspacesQuery.Project.Fragments.toProjectEntity(): FullProjectEntity {
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