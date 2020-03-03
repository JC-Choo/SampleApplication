package dev.chu.memo.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chu.memo.etc.util.EventLiveData

abstract class NewBaseViewModel : ViewModel() {

    private val _liveLoading = MutableLiveData(false)
    val liveLoading: LiveData<Boolean>
        get() = _liveLoading

    val liveToast = EventLiveData<CharSequence>()

    fun showLoading() {
        _liveLoading.value = true
    }

    fun hideLoading() {
        _liveLoading.value = false
    }

    fun showToast(message: CharSequence) {
        liveToast.setEventValue(message)
    }
}