package so.codex.hawk.router

import so.codex.codexbl.entity.ProjectCommon

interface IMainRouter : IBaseRouter {
    fun openProjectFragment(project: ProjectCommon?)
    fun openGarageFragment()
}