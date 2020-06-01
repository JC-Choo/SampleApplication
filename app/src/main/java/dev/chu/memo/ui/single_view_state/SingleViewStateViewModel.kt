package dev.chu.memo.ui.single_view_state

import androidx.lifecycle.MutableLiveData

class SingleViewStateViewModel {
    val test: MutableLiveData<Int> = MutableLiveData()

    init {
        setTest(1)
    }

    fun setTest(_test: Int) {
        test.postValue(_test)
    }
}