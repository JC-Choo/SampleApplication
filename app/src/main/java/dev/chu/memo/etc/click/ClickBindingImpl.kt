package dev.chu.memo.etc.click

import android.util.Pair
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ClickBindingImpl(lifecycle: Lifecycle) : ClickBinding, LifecycleObserver {
    private val publishSubject: PublishSubject<Pair<View, View.OnClickListener>> =
        PublishSubject.create()
    private var disposables: CompositeDisposable = CompositeDisposable()
    private val TIME_OUT: Long = 1000

    init {
        lifecycle.addObserver(this)
    }

    override fun setOnClickListener(view: View, onClickListener: View.OnClickListener) {
        view.setOnClickListener { v -> publishSubject.onNext(Pair.create(view, onClickListener)) }
        disposables.add(publishSubject.throttleFirst(
            TIME_OUT,
            TimeUnit.MILLISECONDS,
            AndroidSchedulers.mainThread()
        )
            .subscribe { pair ->
                if (pair?.first != null && pair.second != null) {
                    pair.second.onClick(pair.first)
                }
            })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyed() {
        if(disposables.isDisposed) disposables.dispose()
    }
}