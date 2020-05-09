package so.codex.hawk.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import so.codex.codexbl.entity.Workspace
import so.codex.hawk.DefaultImageLoader
import so.codex.hawk.R
import so.codex.uicomponent.recyclerview.items.WorkspaceItemView

//ToDo add docs
class WorkspaceItemAdapter : RecyclerView.Adapter<WorkspaceItemAdapter.WorkspaceItemHolder>() {
    class WorkspaceItemHolder(private val view: WorkspaceItemView) : RecyclerView.ViewHolder(view) {
        fun bind(workspace: Workspace) {
            if (!workspace.hasId()) bindAddWorkspaceBtn()
            else bindWorkspace(workspace)
        }

        private fun bindAddWorkspaceBtn() {
            loadAddWorkspaceImage()
            setAddWorkspaceListener()
            setAddWorkspaceData()
        }

        private fun loadAddWorkspaceImage() {
            Picasso.get()
                .load(R.drawable.add_workspace)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(view.logoImage)
        }

        private fun setAddWorkspaceListener() {
            view.setOnClickListener(null)
        }

        private fun setAddWorkspaceData() {
            view.title = "Add workspace"
        }


        private fun bindWorkspace(workspace: Workspace) {
            setWorkspaceListener()
            loadImage(workspace)
            setWorkspaceItemViewData(workspace)
        }

        private fun setWorkspaceListener() { //ToDo Strange Bug (fix)
            view.setOnClickListener {
                Log.d("OOFT", ClickSaver.previousClick?.title ?: "null")
                ClickSaver.previousClick?.disabled()
                ClickSaver.previousClick = view
                view.clicked()
            }
        }

        private fun setWorkspaceItemViewData(workspace: Workspace) {
            view.uuid = workspace.id
            view.title = workspace.name // Don't like both because of level of abstraction (think create 2 methods)
        }

        private fun loadImage(workspace: Workspace) {
            if (workspace.hasImage()) loadLogoImage(workspace)
            else loadDefaultLogoImage(workspace)
        }

        private fun loadLogoImage(workspace: Workspace) {
            Picasso.get()
                .load(workspace.image)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(view.logoImage)
        }

        private fun loadDefaultLogoImage(workspace: Workspace) = view.logoImage.setImageBitmap(
            DefaultImageLoader.get(
                workspace.id,
                workspace.name
            ).loadImage()
        )

        private object ClickSaver {
            var previousClick: WorkspaceItemView? = null
        }
    }


    private val workspaces: MutableList<Workspace> = mutableListOf()

    fun setData(data: List<Workspace>) {
        workspaces.addAll(data)
        notifyDataSetChanged()
    }

    fun setLastElem(lastWorkspace: Workspace) {
        workspaces.add(workspaces.size, lastWorkspace)
        notifyDataSetChanged()
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