package so.codex.codexbl.repository

import android.util.Log
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.codexbl.base.SourceRepository
import so.codex.codexbl.repository.dao.WorkspaceResponseDao
import so.codex.core.UserTokenProvider
import so.codex.sourceinterfaces.IWorkspaceApi
import so.codex.sourceinterfaces.entity.WorkspaceEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

/**
 * For provide information about repository from API
 * @param workspaceApi interface for interaction of API, need for getting information of workspaces
 * @param userTokenProvider used for get access token and refresh token, if it need
 */
class WorkspaceRepository(
    private val workspaceApi: IWorkspaceApi,
    private val userTokenProvider: UserTokenProvider
) : SourceRepository<WorkspaceResponseDao> {
    /**
     * Event for handing actions
     */
    private val eventSubject = PublishSubject.create<WorkspaceEvent>()

    /**
     * Subject that contain actual information from api
     */
    private val subject = BehaviorRelay.create<WorkspaceResponseDao>()

    /**
     * Subscribe on event and handle action
     */
    init {
        eventSubject.flatMapSingle { event ->
            when (event) {
                is WorkspaceEvent.Refresh -> {
                    workspaceApi.getFullWorkspace(userTokenProvider.getToken().accessToken)
                        .compose(userTokenProvider.refreshToken<WorkspaceResponse<WorkspaceEntity>>())
                        .toList()
                        .map(::WorkspaceResponseDao)
                }
                else -> Single.error(UnknownWorkspaceEvent())
            }
        }.subscribe({
            subject.accept(it)
        }, {
            Log.e("WorkspaceProvider", "error ${it::class.java.simpleName}")
            it.printStackTrace()
        })
    }

    override fun refresh() {
        eventSubject.onNext(WorkspaceEvent.Refresh)
    }

    /**
     * Update only selected workspace by [id]
     * @param id of [WorkspaceEntity]
     */
    fun updateWorkspaceById(id: String) {
        eventSubject.onNext(WorkspaceEvent.UpdateWorkspace(id))
    }

    override fun getObservable(): Observable<WorkspaceResponseDao> {
        if (!subject.hasValue()) {
            refresh()
        }
        return subject.hide()
    }

    /**
     * Throwable if we cannot handle current [WorkspaceEvent]
     */
    private class UnknownWorkspaceEvent() : Throwable()

    /**
     * Event for handling actions
     */
    private sealed class WorkspaceEvent {
        /**
         * Refresh all information about [WorkspaceEntity]
         */
        object Refresh : WorkspaceEvent()

        /**
         * Update selected [WorkspaceEntity] by [id]
         * @param id of [WorkspaceEntity]
         */
        data class UpdateWorkspace(val id: String) : WorkspaceEvent()
    }
}