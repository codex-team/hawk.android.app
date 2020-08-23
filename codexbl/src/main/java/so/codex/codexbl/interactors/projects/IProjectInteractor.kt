package so.codex.codexbl.interactors.projects

import io.reactivex.rxjava3.core.Observable
import so.codex.codexbl.entity.Project

interface IProjectInteractor {
    fun getProjects(): Observable<List<Project>>
    fun refresh()
}