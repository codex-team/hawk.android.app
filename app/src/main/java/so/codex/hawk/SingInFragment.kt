package so.codex.hawk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_in.*
import so.codex.hawk.base.BaseFragment

/**
 * Фрагмент, которые отвечает за вход в приложение
 */
class SingInFragment : BaseFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            if(!et_login.text.isNullOrEmpty() && !et_password.text.isNullOrEmpty()){

            }
        }
    }
}