package dev.chu.memo.ui.mvi.aac

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.chu.memo.etc.extension.TAG

abstract class AacMviFragment<STATE, EFFECT, EVENT, ViewModel : AacMviViewModel<STATE, EFFECT, EVENT>, T : ViewDataBinding> :
    Fragment() {

    protected lateinit var binding : T
        private set

    abstract val viewModel: ViewModel

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?)

    abstract fun renderViewState(viewState: STATE)
    abstract fun renderViewEffect(viewEffect: EFFECT)

    private val viewStateObserver = Observer<STATE> {
        Log.d(TAG, "observed viewState $it")
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
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView(view, savedInstanceState, arguments)

        //Registering observers
        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)

    }

}