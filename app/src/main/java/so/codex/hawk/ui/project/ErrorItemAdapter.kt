package so.codex.hawk.ui.project

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import so.codex.codexbl.entity.Event
import so.codex.hawk.adapters.notify

class ErrorItemAdapter: RecyclerView.Adapter<ErrorItemAdapter.ErrorViewHolder>() {
    var dataEvents: List<Event> = listOf()
        set(value) {
            notify(field, value) {left, right ->
                left == right
            }
            field = value
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ErrorViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(event: Event) {

        }
    }
}