package dev.chu.memo.ui.mvi.aac

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dev.chu.memo.etc.extension.TAG

abstract class AacMviActivity<STATE, EFFECT, EVENT, ViewModel: AacMviViewModel<STATE, EFFECT, EVENT>> : AppCompatActivity() {
    abstract val viewModel : ViewModel

    private val viewStateObserver = Observer<STATE> {
        Log.d(TAG, "observed viewStateL $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<EFFECT> {
        Log.d(TAG, "observed viewEffect : $it")
        renderViewEffect(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewStates().observe(this, viewStateObserver)
        viewModel.viewEffects().observe(this, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}