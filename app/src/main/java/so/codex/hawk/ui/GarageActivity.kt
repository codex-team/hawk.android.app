package so.codex.hawk.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_garage.drawer_recycler
import kotlinx.android.synthetic.main.activity_garage.toolbar
import kotlinx.android.synthetic.main.drawer_header.header_email
import kotlinx.android.synthetic.main.drawer_header.header_user_icon
import kotlinx.android.synthetic.main.toolbar_garage.view.toolbar_action_button
import kotlinx.android.synthetic.main.toolbar_garage.view.toolbar_logo
import kotlinx.android.synthetic.main.toolbar_garage.view.toolbar_search
import kotlinx.android.synthetic.main.toolbar_garage.view.toolbar_search_icon
import kotlinx.android.synthetic.main.toolbar_garage.view.toolbar_title
import so.codex.codexbl.entity.Profile
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.presenter.GaragePresenter
import so.codex.codexbl.view.IGarageView
import so.codex.hawk.DefaultImageLoader
import so.codex.hawk.R
import so.codex.hawk.adapters.WorkspaceItemAdapter
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.base.toolbar.CanChangeToolbar
import so.codex.hawk.base.toolbar.ToolbarComponentViewModel
import so.codex.hawk.ui.project.ProjectFragment

/**
 * Main activity that showed after authorization
 */
class GarageActivity : AuthorizedSingleFragmentActivity(), CanChangeToolbar, IGarageView {
    /**
     * Container for fragment
     */
    override val containerId: Int = R.id.container

    /**
     * Presenter of this view
     * @see GaragePresenter
     */
    private val presenter by lazy {
        GaragePresenter()
    }

    /**
     * Adapter for items in list of workspaces
     */
    private val adapter by lazy {
        WorkspaceItemAdapter(object : GaragePresenter.WorkspaceSelectedCallback {
            override fun select(workspace: IGarageView.WorkspaceViewModel) {
                presenter.selectWorkspace(workspace)
            }

        })
    }

    private var toolbarViewModel: ToolbarComponentViewModel = ToolbarComponentViewModel()

    /**
     * Create activity and replace on Garage fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garage)
        setSupportActionBar(toolbar)
        updateToolbar(toolbarViewModel)
        presenter.attached(this)

        presenter.load()
        drawer_recycler.adapter = adapter
        drawer_recycler.layoutManager = LinearLayoutManager(this)
        drawer_recycler.itemAnimator = null
        replaceFragment(ProjectFragment.instance(null))
    }

    /**
     * Shows workspaces in Drawer
     * @param workspaces for showing them in Drawer
     */
    override fun showWorkspaces(workspaces: List<IGarageView.WorkspaceViewModel>) {
        adapter.setData(workspaces)
    }

    /**
     * Shows button at the end of workspaceList
     * When you tap on this button, you can add new workspace
     */
    override fun showAddWorkspace() {
        adapter.setLastElem(presenter.workspaceMapper(Workspace()))
    }

    /**
     * Inflates all needed information in the top of Drawer
     * @param profile current user's profile
     */
    override fun showHeader(profile: Profile) {
        header_email.text = profile.email
        if (profile.picture.trim().isEmpty()) {
            header_user_icon.setImageBitmap(
                DefaultImageLoader.get(profile.id, profile.name).loadImage()
            )
        } else
            Picasso.get()
                .load(profile.picture)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(header_user_icon)
    }

    /**
     * Shows error if it exists
     * @param message message of error
     */
    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * When activity destroy we must
     * prevent app from memory leaks
     */
    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

    /**
     * Update toolbar with showing search view, if it setup, and update state of toolbar
     */
    override fun updateToolbar(viewModel: ToolbarComponentViewModel) {
        if (viewModel.showSearchView) {
            toolbar.toolbar_action_button.setImageResource(R.drawable.ic_back_arrow)
            toolbar.toolbar_action_button.setOnClickListener {
                updateToolbar(
                    viewModel.copy(
                        showSearchView = false
                    )
                )
            }
            toolbar.toolbar_logo.visibility = View.GONE
            toolbar.toolbar_title.visibility = View.GONE
            toolbar.toolbar_search.visibility = View.VISIBLE
            if (viewModel.searchText != "") {
                toolbar.toolbar_search.setText(viewModel.searchText)
            }
            if (!toolbar.toolbar_search.isFocused) {
                toolbar.toolbar_search.requestFocus()
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(
                    toolbar.toolbar_search,
                    InputMethodManager.SHOW_IMPLICIT
                )
            }
            toolbar.toolbar_search_icon.visibility = View.GONE
        } else {
            if (viewModel.menuIcon == -1) {
                toolbar.toolbar_action_button.visibility = View.GONE
            } else {
                toolbar.toolbar_action_button.visibility = View.VISIBLE
                toolbar.toolbar_action_button.setImageResource(viewModel.menuIcon)
            }
            if (viewModel.titleIcon == -1) {
                toolbar.toolbar_logo.visibility = View.GONE
            } else {
                toolbar.toolbar_logo.visibility = View.VISIBLE
                toolbar.toolbar_logo.setImageResource(viewModel.titleIcon)
            }
            toolbar.toolbar_title.visibility = View.VISIBLE
            toolbar.toolbar_search.visibility = View.GONE
            toolbar.toolbar_title.text = viewModel.title
            if (viewModel.showSearchIcon) {
                toolbar.toolbar_search_icon.visibility = View.VISIBLE
                toolbar.toolbar_search_icon.setOnClickListener {
                    updateToolbar(
                        viewModel.copy(
                            showSearchView = true
                        )
                    )
                }
            } else {
                toolbar.toolbar_search_icon.visibility = View.GONE
            }
        }
        toolbarViewModel = viewModel
    }

    override fun onBackPressed() {
        if (toolbarViewModel.showSearchView) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                toolbar.toolbar_search.windowToken,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            updateToolbar(
                toolbarViewModel.copy(
                    showSearchView = false
                )
            )
        } else {
            super.onBackPressed()
        }
    }
}
