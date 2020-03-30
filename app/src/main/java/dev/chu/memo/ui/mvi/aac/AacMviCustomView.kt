package dev.chu.memo.ui.mvi.aac

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.chu.memo.etc.extension.TAG


abstract class AacMviCustomView<STATE, EFFECT, EVENT, ViewModel : AacMviViewModel<STATE, EFFECT, EVENT>> {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        Log.d(TAG, "observed viewState : $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<EFFECT> {
        Log.d(TAG, "observed viewEffect : $it")
        renderViewEffect(it)
    }

    fun startObserving(lifecycleOwner: LifecycleOwner) {
        //Registering observers
        viewModel.viewStates().observe(lifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(lifecycleOwner, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}