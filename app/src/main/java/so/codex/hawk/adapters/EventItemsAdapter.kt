package so.codex.hawk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import so.codex.codexbl.entity.Event
import so.codex.hawk.R
import kotlin.properties.Delegates

/**
 * UI element for showing events
 */
class EventItemsAdapter : RecyclerView.Adapter<EventItemsAdapter.EventItemViewHolder>() {

    /**
     * List of event that showed in RecyclerView. Used delegate for change old elements in the list on new elements
     * and update element on this position.
     */
    private var data: List<Event> by Delegates.observable(listOf()) { _, old, new ->
        notify(old, new) { left, right ->
            left == right
        }

    }

    /**
     * Create view from module "uicomponent" for event item view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_event_item, parent, false)

        return EventItemViewHolder(view)
    }

    /**
     * Get size of projects elements in data
     */
    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * Bind data with holder by position
     */
    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    /**
     * Class that representation of event in recycler view
     */
    class EventItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Data binding [event] with UI elements
         * @param event Project that showed in UI
         */
        fun bind(event: Event) {
            val eventTitle = itemView.findViewById<TextView>(R.id.tv_event_title)
            eventTitle.text = event.payload.title
        }
    }


}