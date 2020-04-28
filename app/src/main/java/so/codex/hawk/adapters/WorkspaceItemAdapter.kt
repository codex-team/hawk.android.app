package so.codex.hawk.adapters

import android.os.WorkSource
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import so.codex.codexbl.entity.Workspace
import so.codex.hawk.R
import so.codex.uicomponent.recyclerview.items.WorkspaceItemView

class WorkspaceItemAdapter : RecyclerView.Adapter<WorkspaceItemAdapter.WorkspaceItemHolder>() {
    inner class WorkspaceItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(workspace: Workspace) {
            if (view is WorkspaceItemView) {
                if (workspace.image == "NO_IMG") {
                    view.title = workspace.name
                    Picasso.get()
                        .load(R.drawable.add_workspace)
                        .into(view.logoImage)

                    view.isClickable = false

                    //ToDo add listener
                    view.logoImage.setOnClickListener {
                        Toast.makeText(view.context, "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    return
                }

                view.setOnClickListener {
                    Log.d("OOF", v.toString())
                    v?.disabled()
                    v = view
                    view.clicked()
                }

                view.uuid = workspace.id
                view.title = workspace.name
                if (workspace.image.isNotEmpty()) {
                    Picasso.get()
                        .load(workspace.image)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(view.logoImage)
                } else view.setDefaultImage()
            }
        }
    }

    var v: WorkspaceItemView? = null

    private val workspaces: MutableList<Workspace> = mutableListOf()

    fun setData(data: List<Workspace>) {
        workspaces.addAll(data)
        notifyDataSetChanged()
    }

    //ToDo create maybe with viewType
    fun setLastElem(lastWorkspace: Workspace) {
        workspaces.add(workspaces.size, lastWorkspace)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceItemHolder {
        val view =
            WorkspaceItemView(parent.context)

        return WorkspaceItemHolder(view)
    }

    override fun getItemCount(): Int = workspaces.size

    override fun onBindViewHolder(holder: WorkspaceItemHolder, position: Int) {
        holder.bind(workspaces[position])
    }
}