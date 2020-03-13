package dev.chu.memo.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    protected val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}