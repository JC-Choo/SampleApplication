package dev.chu.memo.ui.mvi.aac

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import dev.chu.memo.etc.extension.TAG

abstract class AacMviActivity<STATE, EFFECT, EVENT, ViewModel : AacMviViewModel<STATE, EFFECT, EVENT>, T : ViewDataBinding> :
    AppCompatActivity() {

    protected lateinit var binding: T
        private set

    abstract val viewModel: ViewModel

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun initView()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this

        viewModel.viewStates().observe(this, viewStateObserver)
        viewModel.viewEffects().observe(this, viewEffectObserver)

        initView()
    }
}