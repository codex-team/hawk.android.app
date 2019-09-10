package so.codex.codexbl.interactors

import org.koin.core.KoinComponent
import so.codex.codexbl.entity.Event
import so.codex.codexbl.entity.Project
import so.codex.codexbl.interactors.interfaces.IProjectInteractor
import java.util.*

class ProjectInteractor : IProjectInteractor, KoinComponent {


    override fun getProjectInfo(): Project {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProjectEvents(): List<Event> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEventPoints(): Map<Date, List<Int>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}