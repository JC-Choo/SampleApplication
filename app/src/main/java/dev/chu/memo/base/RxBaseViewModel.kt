package dev.chu.memo.base

import io.reactivex.disposables.CompositeDisposable

abstract class RxBaseViewModel {

    protected val compositeDisposable = CompositeDisposable()

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}