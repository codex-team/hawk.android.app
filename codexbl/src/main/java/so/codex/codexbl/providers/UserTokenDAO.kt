package so.codex.codexbl.providers

import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.entity.UserToken

/**
 * Интерфейс, в котором определены основные методы для взаимодействия с хранилищем, где хранится
 * основная информация о пользователе вместе с сессией
 */
interface UserTokenDAO {
    /**
     * Получение объекта [UserToken], в котором хранятся токен и refresh токен
     * @return возвращает [UserToken], если удалось извлечь из хранилища информацию о токене и
     * refresh токене, иначе вернет null
     */
    fun getUserToken(): UserToken?

    /**
     * Для сохранения токена в хранищиле
     * @param userToken обязательно в себе хранит токен и refreshToken для сохранения его в
     * хранилище
     * @return Возвращает true, если ему удалось успешно сохранить данные в хранилище, в противном
     * случае вернет false
     */
    fun saveUserToken(userToken: UserToken): Boolean

    /**
     * Для сохранения пользовательской сессии в хранилище
     * @param sessionData содержит в себе ту же информацию, что и [UserToken], но с дополнительной
     * информацией
     * @return Возвращает true, если ему удалось успешно сохранить данные в хранилище, в противном
     * случае вернет false
     */
    fun saveSession(sessionData: SessionData): Boolean

    /**
     * Для получения последней сохраненной сессии
     * @return Возвращает заполненный [SessionData], если ему удалось успешно получить данные из
     * хранилище, в противном случае вернет null
     */
    fun getLastSession(): SessionData?

    /**
     * Очищает все пользовательские данные с хранилища
     */
    fun clean()

}