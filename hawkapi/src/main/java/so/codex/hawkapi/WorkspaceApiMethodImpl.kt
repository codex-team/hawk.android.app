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

/**
 * Класс, который использует [ApolloClient] для отправки GraphQL запросов, конвертирует их в RxJava2.
 * Также определены расширения для некоторых объектов, для более удобного конвертирования из одного
 * объекта в другой. Реализуте интерфейс [WorkspacesApiMethods] для реализции отправки запросов.
 * @constructor принимает [ApolloClient] для отправки GraphQL запросов
 * @see WorkspacesApiMethods
 * @author Shiplayer
 */
class WorkspaceApiMethodImpl(private val apolloClient: ApolloClient) : WorkspacesApiMethods {
    override fun getWorkspaces(token: String): Observable<WorkspaceResponse<FullWorkspaceEntity>> {
        TokenInterceptor.instance.updateToken(token)
        return apolloClient.query(
                GetWorkspacesQuery.builder()
                        .build()
        ).toRxJava()
                .handleHttpErrorsSingle().map {
                    mutableListOf<WorkspaceResponse<FullWorkspaceEntity>>().apply {
                        it.workspaces?.map { it!! }?.forEach {
                            add(it.toFullWorkspaceResponse())
                        } ?: listOf<WorkspaceResponse<FullWorkspaceEntity>>()
                    }
                }.flattenAsObservable { it }
    }

    //TODO вынести расширения для данных объектов в отдельный файл и настроить уровень доступа только для данного модуля
    /**
     * Конвертирует из [GetWorkspacesQuery.Workspace] в ответ, в который обернут [FullWorkspaceEntity]
     * @return Возвращает [WorkspaceResponse] с указанным типом [FullWorkspaceEntity]
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
                            it!!.fragments().toProjectEntity()
                        } ?: listOf()
                )
        )
    }

    /**
     * Конвертирует из [GetWorkspacesQuery.Project.Fragments] в [ProjectEntity]
     */
    private fun GetWorkspacesQuery.Project.Fragments.toProjectEntity(): ProjectEntity {
        return ProjectEntity(
                this.projectsList.id(),
                "",
                this.projectsList.name(),
                projectsList.description() ?: "",
                "",
                this.projectsList.image() ?: "",
                null,
                this.projectsList.events()?.map {
                    it.fragments().toEventEntity()
                } ?: listOf()
        )
    }

    /**
     * Конвертирует из [ProjectsList.Event.Fragments] в [ProjectEntity]
     */
    private fun ProjectsList.Event.Fragments.toEventEntity(): EventEntity {
        return EventEntity(
                this.eventsList().id(),
                "",
                this.eventsList().payload().toEventPayloadEntity()
        )
    }

    /**
     * Конвертирует из [EventsList.Payload] в [EventPayloadEntity]
     */
    private fun EventsList.Payload.toEventPayloadEntity(): EventPayloadEntity {
        return EventPayloadEntity(
                this.title(),
                this.timestamp()
        )
    }
}