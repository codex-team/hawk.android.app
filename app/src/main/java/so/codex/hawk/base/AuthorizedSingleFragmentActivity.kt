package so.codex.hawk.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import so.codex.codexbl.presenter.AuthorizedPresenter
import so.codex.codexbl.view.IAuthorizedView
import so.codex.hawk.router.ILogoutRouter
import so.codex.hawk.ui.login.LoginActivity

/**
 * Абстрактный класс, который отвечает за то, авторизирован ли пользователь. Также расширает
 * класс [BaseSingleFragmentActivity] и интерфейс [ILogoutRouter]
 */
abstract class AuthorizedSingleFragmentActivity : BaseSingleFragmentActivity(), ILogoutRouter {
    /**
     * Реализация [IAuthorizedView] как анонимный класс, чтобы другие активити не могли получить
     * к нему доступ
     */
    private val mAuthorizedView: IAuthorizedView = object : IAuthorizedView {
        override fun showErrorMessage(message: String) {

        }

        override fun logout() {
            this@AuthorizedSingleFragmentActivity.logout()
        }

    }

    /**
     * Презентор, которые проверяет авторизирован ли пользователь
     */
    private val mAuthPresenter = AuthorizedPresenter()

    /**
     * Во время создания активити, презентор [mAuthPresenter] присоединяется к [mAuthorizedView]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuthPresenter.attached(mAuthorizedView)
    }

    override fun onStart() {
        super.onStart()
        if (mAuthPresenter.view == null) {
            mAuthPresenter.attached(mAuthorizedView)
        }
    }

    override fun onStop() {
        super.onStop()
        mAuthPresenter.detached()
    }

    /**
     * Если пользователь нажал на кнопку выхода, то отправляем событие в презентор [mAuthPresenter]
     */
    fun pressLogout() {
        mAuthPresenter.clearAndLogout()
    }

    /**
     * Во время события выхода, удаляем все задачи в стеке и запускаем активити [LoginActivity]
     */
    override fun logout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask()
        } else {
            finishAffinity()
        }
        startActivity(Intent(this, LoginActivity::class.java))
    }
}