package so.codex.codexbl.interactors.projects

import io.reactivex.rxjava3.core.Observable
import so.codex.codexbl.entity.Project
import so.codex.codexbl.providers.projects.ProjectProvider

class ProjectInteractor(private val projectProvider: ProjectProvider) : IProjectInteractor {

    override fun getProjects(): Observable<List<Project>> {
        return projectProvider.getProjects()
    }

    override fun refresh() {
        projectProvider
    }
}