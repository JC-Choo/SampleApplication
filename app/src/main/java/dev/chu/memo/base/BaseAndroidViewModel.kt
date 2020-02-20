package dev.chu.memo.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
        disposable.clear()
        super.onCleared()
    }
}