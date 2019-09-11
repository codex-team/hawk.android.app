package so.codex.sourceinterfaces

import io.reactivex.Single
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.TokenResponse

/**
 * Интерфейс с обявленными методами для взаимодействия с авторизацией, регистрации и обновлением
 * токена.
 * @author Shiplayer
 */
interface IAuthApi {
    /**
     * Отправляет запрос на авторизацию
     * @param auth Объект, по которому берется почта и пароль для авторизации
     * @return Возвращает [Single] с объектом [TokenResponse], в котором хранится токен и refresh
     * токен для обновления сессии
     */
    fun login(auth: AuthEntity): Single<TokenResponse>

    /**
     * Отправляется запрос на регистрацию нового пользователя
     * @param signUp Содержит email, по которому будет зарегистрирован новый пользователь
     * @return Возвращает [Single] с булевским значением, true если пользователь зарегистрирован,
     * false иначе
     */
    fun signUp(signUp: SignUpEntity): Single<Boolean>

    /**
     * Отправляет запрос на обновление токена
     * @param token Объект содержит refresh токен необходимого для обновления токен для API запросов
     * @return Возвращает [Single] с объектом [TokenResponse], содержит access token и refresh token
     */
    fun refreshToken(token: TokenEntity): Single<TokenResponse>
}