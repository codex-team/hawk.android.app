package so.codex.hawkapi

import com.apollographql.apollo.ApolloClient
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.core.UserTokenProvider
import so.codex.hawkapi.api.workspace.WorkspacesApiMethods
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.OnlyWorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse
import java.util.Date

/**
 * Class that used [ApolloClient] for sending GraphQL request and converted response to RxJava2.
 * Implementation interface [WorkspacesApiMethods] for handling and converting requests and responses to RxJava2.
 * @see WorkspacesApiMethods
 * @author Shiplayer
 */
class WorkspaceApiMethodImpl(private val apolloClient: ApolloClient) : WorkspacesApiMethods,
    KoinComponent {

    /**
     * Use for getting access token
     */
    private val userTokenProvider by inject<UserTokenProvider>()

    /**
     * Send request used by [apolloClient] for getting workspace information of user
     * @param token Token
     * @param limit Limited number of workspaces that we can get from the api
     * @param skip Count of workspaces that need to skipped
     * @return Observable with [WorkspaceResponse]
     */
    override fun getWorkspaces(
        token: String,
        limit: Int,
        skip: Int
    ): Observable<WorkspaceResponse<FullWorkspaceEntity>> {
        return apolloClient.retryQuery(
            GetFullWorkspacesQuery(),
            userTokenProvider
        ).handleHttpErrorsSingle().map {
            mutableListOf<WorkspaceResponse<FullWorkspaceEntity>>().apply {
                it.workspaces?.filterNotNull()?.forEach {
                    add(it.toFullWorkspaceResponse())
                } ?: listOf<WorkspaceResponse<FullWorkspaceEntity>>()
            }
        }.flattenAsObservable { it }
    }

    override fun getWorkspacesWithProjects(token: String): Observable<WorkspaceResponse<WorkspaceWithProjectsEntity>> {
        TokenInterceptor.instance.updateToken(token)
        return apolloClient.retryQuery(
            GetWorkspacesWithProjectsQuery()
        ).handleHttpErrorsSingle().map {
            mutableListOf<WorkspaceResponse<WorkspaceWithProjectsEntity>>().apply {
                it.workspaces?.filterNotNull()?.forEach {
                    add(it.toWorkspacesWithProjectsResponse())
                } ?: listOf<WorkspaceResponse<WorkspaceWithProjectsEntity>>()
            }
        }.flattenAsObservable { it }
    }

    override fun getOnlyWorkspace(
        token: String,
        limit: Int,
        skip: Int
    ): Observable<WorkspaceResponse<OnlyWorkspaceEntity>> {
        TokenInterceptor.instance.updateToken(token)
        return apolloClient.retryQuery(
            GetOnlyWorkspaceQuery()
        ).handleHttpErrorsSingle().map {
            mutableListOf<WorkspaceResponse<OnlyWorkspaceEntity>>().apply {
                it.workspaces?.filterNotNull()?.forEach {
                    add(it.toOnlyWorkspaceResponse())
                } ?: listOf<WorkspaceResponse<FullWorkspaceEntity>>()
            }
        }.flattenAsObservable { it }
    }

    //TODO вынести расширения для данных объектов в отдельный файл и настроить уровень доступа только для данного модуля
}