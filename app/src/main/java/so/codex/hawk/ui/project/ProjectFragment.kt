package so.codex.hawk.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import so.codex.hawk.R
import so.codex.hawk.base.BaseFragment

class ProjectFragment : BaseFragment() {

    companion object {
        fun instance() = ProjectFragment()
    }

    override fun showErrorMessage(message: String) {

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_project, container, false).also {
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}