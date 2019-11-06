package so.codex.codexbl.view.project

import so.codex.codexbl.entity.Event
import so.codex.codexbl.view.base.IBaseView

interface IProjectView : IBaseView {
    fun showPlotData()

    fun showHawkErrors(events: List<Event>)

    fun openEvent(event: Event)
}