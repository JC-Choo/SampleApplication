package dev.chu.memo.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}