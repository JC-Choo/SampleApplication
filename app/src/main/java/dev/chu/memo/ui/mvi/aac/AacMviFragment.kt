package dev.chu.memo.ui.mvi.aac

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.chu.memo.etc.extension.TAG

abstract class AacMviFragment<STATE, EFFECT, EVENT, ViewModel : AacMviViewModel<STATE, EFFECT, EVENT>> :
    Fragment() {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        Log.d(TAG, "observed viewState : $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<EFFECT> {
        Log.d(TAG, "observed viewEffect : $it")
        renderViewEffect(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Registering observers
        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}