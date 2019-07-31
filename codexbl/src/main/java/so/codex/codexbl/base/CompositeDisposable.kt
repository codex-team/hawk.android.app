package so.codex.codexbl.base

import io.reactivex.disposables.Disposable

class CompositeDisposable {
    private val list = mutableListOf<Disposable>()

    final fun of(disposable: Disposable){
        list.add(disposable)
    }

    final fun dispose(){
        list.forEach {
            it.dispose()
        }
    }
}