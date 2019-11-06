package so.codex.hawk.ui.project

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoContext
import so.codex.codexbl.entity.Event
import so.codex.hawk.R
import so.codex.hawk.adapters.notify
import so.codex.hawk.ui.anko.ErrorItemView

class ErrorItemAdapter : RecyclerView.Adapter<ErrorItemAdapter.ErrorViewHolder>() {
    var dataEvents: List<Event> = listOf()
        set(value) {
            notify(field, value) { left, right ->
                left == right
            }
            field = value
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ErrorViewHolder(ErrorItemView<ErrorItemAdapter>().createView(
                AnkoContext.Companion.create(parent.context, this)
        ))

    override fun getItemCount() = dataEvents.size

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        holder.bind(dataEvents[position])
    }

    class ErrorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageView = view.findViewById<TextView>(ErrorItemView.messageId)
        private val countView = view.findViewById<TextView>(ErrorItemView.countId)
        private val imageView = view.findViewById<ImageView>(ErrorItemView.imageId)

        fun bind(event: Event) {
            messageView.text = event.payload.title
            countView.text = "9999"
            imageView.setImageResource(R.drawable.ic_bell)
        }
    }
}