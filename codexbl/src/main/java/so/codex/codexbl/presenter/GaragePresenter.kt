package so.codex.codexbl.presenter

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.interactors.IProfileInteractor
import so.codex.codexbl.interactors.IWorkspaceInteractor
import so.codex.codexbl.interactors.ProfileInteractor
import so.codex.codexbl.view.IGarageView
import java.lang.Exception

/**
 * Presenter for communication with Garage UI
 * @author YorkIsMine
 */
class GaragePresenter : BasePresenter<IGarageView>(), KoinComponent {
    /**
     * Interactor for getting workspaces
     */
    private val workspaceInteractor: IWorkspaceInteractor by inject()

    /**
     * Interactor for getting information about profile
     */
    private val profileInteractor: IProfileInteractor = ProfileInteractor() //ToDo to put in Koin

    /**
     * Load all needed data
     */
    fun load() {
        loadAllWorkspaces()
        loadProfilePanel()
    }

    /**
     * Getting all workspaces
     */
    private fun loadAllWorkspaces() {
        compositeDisposable.of(
            workspaceInteractor
                .getWorkspaces()
                .subscribe({
                    if (!it.isNullOrEmpty())
                        view?.showWorkspaces(it)
                    view?.showAddWorkspace()
                }, {
                    it.printStackTrace()
                    view?.showErrorMessage(it.message ?: " error")
                })
        )
    }

    /**
     * Getting profile for header
     */
    private fun loadProfilePanel() {
        compositeDisposable.of(
            profileInteractor.getProfile()
                .subscribe({
                    view?.showHeader(it)
                }, {
                    view?.showErrorMessage(it.message ?: "Error with getting Profile")
                })
        )
    }

    /**
     * Dispose of all RxJava Streams/Calls
     */
    fun unsubscribe() {
        compositeDisposable.dispose()
    }
}