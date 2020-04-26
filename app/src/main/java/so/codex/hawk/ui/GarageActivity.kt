package so.codex.hawk.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_garage.*
import kotlinx.android.synthetic.main.toolbar_garage.view.*
import so.codex.hawk.R
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.base.toolbar.CanChangeToolbar
import so.codex.hawk.base.toolbar.ToolbarComponentViewModel
import so.codex.hawk.ui.project.ProjectFragment

/**
 * Main activity that showed after authorization
 */
class GarageActivity : AuthorizedSingleFragmentActivity(), CanChangeToolbar {
    /**
     * Container for fragment
     */
    override val containerId: Int = R.id.container

    private var toolbarViewModel: ToolbarComponentViewModel = ToolbarComponentViewModel()

    /**
     * Create activity and replace on Garage fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garage)
        setSupportActionBar(toolbar)
        updateToolbar(toolbarViewModel)
        replaceFragment(ProjectFragment.instance(null))
    }

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
