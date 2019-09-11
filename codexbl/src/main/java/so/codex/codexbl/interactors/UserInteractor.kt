package so.codex.codexbl.interactors

import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.providers.UserTokenDAO

/**
 * Интерактор, в котором реализован интерфейс [IUserInteractor], благодаря которму реализовано
 * сохранение, получения и очистки данных для сессии
 * @author Shiplayer
 */
class UserInteractor : IUserInteractor, KoinComponent {
    /**
     * Объект, который предоставляет доступ и способ сохранения и получения данных
     */
    private val userTokenDAO: UserTokenDAO by inject(named("preferences"))

    /**
     * Метод для сохрани сессии
     * @param session получает объект [SessionData], в котором указана вся необходимая информация
     * @return возвращает true, если сохранение удалось успешным, false в противном случаи
     */
    override fun saveSession(session: SessionData): Boolean {
        Log.i("UserInteractor", "saveSession = $session")
        return userTokenDAO.saveSession(session)
    }

    /**
     * Метод для получения последней сессии
     * @return Возвращает объект [SessionData] или null, если последней сессии не было найдено
     */
    override fun getLastSession(): SessionData? {
        return userTokenDAO.getLastSession().also {
            Log.i("UserInteractor", "data = $it")
        }
    }

    /**
     * Очищает всю информацию, хранимой в данной структуре
     */
    override fun clear() {
        userTokenDAO.clean()
    }
}