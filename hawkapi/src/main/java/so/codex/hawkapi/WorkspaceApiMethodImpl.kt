package so.codex.hawkapi

import com.apollographql.apollo.ApolloClient
import io.reactivex.Observable
import so.codex.hawkapi.api.TokenInterceptor
import so.codex.hawkapi.api.workspace.WorkspacesApiMethods
import so.codex.hawkapi.fragment.EventsList
import so.codex.hawkapi.fragment.ProjectsList
import so.codex.sourceinterfaces.entity.EventEntity
import so.codex.sourceinterfaces.entity.EventPayloadEntity
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.ProjectEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse
import java.util.*

/**
 * Class that used [ApolloClient] for sending GraphQL request and converted response to RxJava2.
 * Implementation interface [WorkspacesApiMethods] for handling and converting requests and responses to RxJava2.
 * @see WorkspacesApiMethods
 * @author Shiplayer
 */
class WorkspaceApiMethodImpl(private val apolloClient: ApolloClient) : WorkspacesApiMethods {
    override fun getWorkspaces(
        token: String,
        limit: Int,
        skip: Int
    ): Observable<WorkspaceResponse<FullWorkspaceEntity>> {
        TokenInterceptor.instance.updateToken(token)
        return apolloClient.retryQuery(
            GetWorkspacesQuery(limit = limit, skip = skip)
        ).handleHttpErrorsSingle().map {
            mutableListOf<WorkspaceResponse<FullWorkspaceEntity>>().apply {
                it.workspaces?.map { it!! }?.forEach {
                    add(it.toFullWorkspaceResponse())
                } ?: listOf<WorkspaceResponse<FullWorkspaceEntity>>()
            }
        }.flattenAsObservable { it }
    }

    //TODO вынести расширения для данных объектов в отдельный файл и настроить уровень доступа только для данного модуля
    /**
     * Convert from [GetWorkspacesQuery.Workspace] to [FullWorkspaceEntity] that wrapped to response instance
     * @return [WorkspaceResponse] with type of [FullWorkspaceEntity]
     */
    private fun GetWorkspacesQuery.Workspace.toFullWorkspaceResponse(): WorkspaceResponse<FullWorkspaceEntity> {
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
     * Convert from [GetWorkspacesQuery.Project.Fragments] to [ProjectEntity]
     */
    private fun GetWorkspacesQuery.Project.Fragments.toProjectEntity(): ProjectEntity {
        return ProjectEntity(
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
}