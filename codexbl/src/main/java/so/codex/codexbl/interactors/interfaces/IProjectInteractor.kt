package so.codex.codexbl.interactors.interfaces

import so.codex.codexbl.entity.Event
import so.codex.codexbl.entity.Project
import java.util.*

interface IProjectInteractor {
    fun getProjectInfo(): Project

    fun getProjectEvents(): List<Event>

    fun getEventPoints(): Map<Date, List<Int>>
}