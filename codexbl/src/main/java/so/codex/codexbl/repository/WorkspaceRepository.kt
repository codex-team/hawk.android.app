package so.codex.codexbl.repository

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

class WorkspaceRepository(
    private val workspaceApi: IWorkspaceApi,
    private val userTokenProvider: UserTokenProvider
) : SourceRepository<WorkspaceResponseDao> {
    private val eventSubject = PublishSubject.create<WorkspaceEvent>()
    private val subject = BehaviorRelay.create<WorkspaceResponseDao>()

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
        }.subscribe {
            subject.accept(it)
        }
    }

    override fun refresh() {
        eventSubject.onNext(WorkspaceEvent.Refresh)
    }

    fun updateWorkspaceById(id: String) {
        eventSubject.onNext(WorkspaceEvent.UpdateWorkspace(id))
    }

    override fun getObservable(): Observable<WorkspaceResponseDao> {
        if (!subject.hasValue()) {
            refresh()
        }
        return subject.hide()
    }


    private class UnknownWorkspaceEvent() : Throwable()

    private sealed class WorkspaceEvent {
        object Refresh : WorkspaceEvent()
        data class UpdateWorkspace(val id: String) : WorkspaceEvent()
    }
}