package so.codex.codexbl.presenter

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.interactors.IProfileInteractor
import so.codex.codexbl.interactors.IWorkspaceInteractor
import so.codex.codexbl.interactors.ProfileInteractor
import so.codex.codexbl.view.IGarageView
import so.codex.codexbl.view.IGarageView.WorkspaceViewModel

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
    private val profileInteractor: IProfileInteractor by inject()

    /**
     * Stores current tapped workspace in Drawer
     */
    private var selectedWorkspace: WorkspaceViewModel? = null

    /**
     * Workspaces which were cached
     * @see WorkspaceViewModel
     */
    private var cachedWorkspaces: List<WorkspaceViewModel> = listOf()

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
                .map {
                    it.map(this::workspaceMapper)
                }
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        view?.showWorkspaces(it)
                        cachedWorkspaces = it
                    }
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
     * When user click this method will execute to bold item
     * @param workspaceViewModel given wVM
     * @see WorkspaceViewModel
     */
    fun selectWorkspace(workspaceViewModel: WorkspaceViewModel) {
        selectedWorkspace = workspaceViewModel
        cachedWorkspaces.map {
            if (it.id == selectedWorkspace?.id)
                it.copy(isSelected = true)
            else
                it
        }.apply {
            view?.showWorkspaces(this)
        }
    }

    /**
     * Maps workspace to workspaceViewModel
     * @param workspace mappable workspace
     */
    fun workspaceMapper(workspace: Workspace): WorkspaceViewModel { //ToDo seems it is not good solution
        return WorkspaceViewModel(
            workspace.id,
            workspace.name,
            workspace.image,
            workspace.id == selectedWorkspace?.id
        )
    }


    /**
     * Dispose of all RxJava Streams/Calls
     */
    fun unsubscribe() {
        compositeDisposable.dispose()
    }

    /**
     * Callback for selecting items in drawer
     */
    interface WorkspaceSelectedCallback {
        /**
         * Used when user taps on item in drawer
         */
        fun select(workspace: WorkspaceViewModel)
    }
}