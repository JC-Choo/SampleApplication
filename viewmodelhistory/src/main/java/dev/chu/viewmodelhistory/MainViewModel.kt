package dev.chu.viewmodelhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chu.viewmodelhistory.livedata.Event
import dev.chu.viewmodelhistory.livedata.MutableSingleLiveData
import dev.chu.viewmodelhistory.livedata.SingleLiveData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    /**
     * Step 1 LiveData + Event
     */
    private val _test01 = MutableLiveData<Event<String>>()
    val test01: LiveData<Event<String>>
        get() = _test01

    fun setTest01(text: String) {
        _test01.value = Event(text)
    }

    /**
     * Step 2 SingleLiveData
     */
    private val _test02 = MutableSingleLiveData<String>()
    val test02: SingleLiveData<String>
        get() = _test02

    /**
     * Step 3 SharedFlow
     */
    private val _test03 = MutableSharedFlow<String>()
    val test03 = _test03.asSharedFlow()

    fun getTest03() {
        viewModelScope.launch { _test03.emit("test03") }
    }

    /**
     * Step 4 SharedFlow + Sealed class
     */

    /**
     * Step 5 SharedFlow + Sealed class + Lifecycle
     */

    /**
     * Step 6 EventFlow + Sealed class + Lifecycle
     */

}