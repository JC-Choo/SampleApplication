package dev.chu.memo.ui.mvi.aac

import android.app.Application
import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseAndroidViewModel
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.ui.mvi.etc.NoObserverAttachedException
import dev.chu.memo.ui.mvi.etc.ViewModelContract

/**
 * AacMviViewModel
 * ViewModel을 생성할 기본 클래스
 * 3 클래스(STATE, EFFECT, EVENT)가 필요
 */

open class AacMviViewModel<STATE, EFFECT, EVENT>(application: Application) :
    BaseAndroidViewModel(application),
    ViewModelContract<EVENT> {

    private val _viewStates: MutableLiveData<STATE> = MutableLiveData()
    fun viewStates(): LiveData<STATE> = _viewStates

    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            Log.d(TAG, "setting viewState : $value")
            _viewState = value
            _viewStates.value = value
        }

    private val _viewEffects: SingleLiveEvent<EFFECT> =
        SingleLiveEvent()
    fun viewEffects(): SingleLiveEvent<EFFECT> = _viewEffects

    private var _viewEffect: EFFECT? = null
    protected var viewEffect: EFFECT
        get() = _viewEffect ?: throw UninitializedPropertyAccessException("\"viewEffect\" was queried before being initialized")
        set(value) {
            Log.d(TAG, "setting viewEffect : $value")
            _viewEffect = value
            _viewEffects.value = value
        }

    @CallSuper
    override fun process(viewEvent: EVENT) {
        if (!viewStates().hasObservers()) {
            throw NoObserverAttachedException("No observer attached. In case of custom View \"startObserving()\" function needs to be called manually.")
        }
        Log.d(TAG, "processing viewEvent: $viewEvent")
    }
}