package so.codex.hawk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import so.codex.codexbl.entity.Event
import so.codex.hawk.R
import so.codex.uicomponent.recyclerview.items.EventItemView
import kotlin.properties.Delegates

/**
 * UI element for showing events
 */
class EventItemsAdapter : RecyclerView.Adapter<EventItemsAdapter.EventItemViewHolder>() {

    private var data: List<Event> by Delegates.observable(listOf()){ _, old, new ->
        notify(old, new){ left, right ->
            left == right
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_event_item, parent, false)

        return EventItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class EventItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //ToDo think about incrementing count of the same errors

        fun bind(event: Event){
            val eventTitle = itemView.findViewById<TextView>(R.id.tv_event_title)
            eventTitle.text = event.payload.title
        }
    }


}