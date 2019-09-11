package so.codex.codexbl.entity

/**
 * Структура данных, которая использутеся для передачи данных со слоя Presenter к слою Interactor
 * @author Shiplayer
 */
data class UserAuth(val email: String, val password: String)