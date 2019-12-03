package so.codex.hawk.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import so.codex.hawk.base.BaseFragment

abstract class ToolbarFragment() : BaseFragment() {

    private var toolbar: View? = null

    open fun hasToolbar(): Boolean = false

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}