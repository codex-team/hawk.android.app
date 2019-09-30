package so.codex.codexbl.base

import io.reactivex.disposables.Disposable

/**
 * Composition of [Disposable] created for simplify storage and quick completion of rx streams
 * @author Shiplayer
 */
class CompositeDisposable {
    private val list = mutableListOf<Disposable>()

    /**
     * Добавить Disposable к списку
     */
    final fun of(disposable: Disposable){
        list.add(disposable)
    }

    /**
     * Завершить все потоки
     */
    final fun dispose(){
        list.forEach {
            it.dispose()
        }
    }
}