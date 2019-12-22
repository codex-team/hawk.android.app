package so.codex.hawk.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import so.codex.hawk.R
import so.codex.hawk.base.BaseFragment

class ProjectFragment : BaseFragment() {

    companion object {

        public final const val PROJECT_ID_ARGUMENTS_KEY = "project_id_arguments_key"

        fun instance(id: String) =
            ProjectFragment().apply {
                arguments = Bundle().apply {
                    putString(PROJECT_ID_ARGUMENTS_KEY, id)
                }
            }
    }

    private val projectId by lazy {
        arguments?.getString(PROJECT_ID_ARGUMENTS_KEY, "")!!
    }

    override fun showErrorMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_project, container, false)
    }
}