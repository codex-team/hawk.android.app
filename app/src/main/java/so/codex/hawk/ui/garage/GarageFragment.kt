package so.codex.hawk.ui.garage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_garage.*
import so.codex.hawk.R
import so.codex.hawk.adapters.ProjectItemsAdapter
import so.codex.hawk.base.BaseFragment

class GarageFragment : BaseFragment() {

    companion object {
        fun instance() = GarageFragment()
    }

    override fun showErrorMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_garage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_project_list.adapter = ProjectItemsAdapter()
        rv_project_list.layoutManager = LinearLayoutManager(context)

    }
}