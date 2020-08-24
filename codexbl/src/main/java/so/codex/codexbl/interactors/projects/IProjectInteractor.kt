package so.codex.codexbl.interactors.projects

import io.reactivex.rxjava3.core.Observable
import so.codex.codexbl.entity.Project

/**
 * Interface for communication with presentation layer
 */
interface IProjectInteractor {
    /**
     * Get all projects
     * @return Stream of list of projects
     */
    fun getProjects(): Observable<List<Project>>

    /**
     * Need to refresh list of projects
     */
    fun refresh()
}