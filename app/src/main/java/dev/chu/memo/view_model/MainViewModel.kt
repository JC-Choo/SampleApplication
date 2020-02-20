package dev.chu.memo.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseAndroidViewModel
import dev.chu.memo.data.local.MemoData
import dev.chu.memo.data.repository.MainRepository
import dev.chu.memo.etc.listener.DataListener

class MainViewModel(application: Application) : BaseAndroidViewModel(application) {
    private val repository by lazy { MainRepository(application) }

    private var _personList: MutableLiveData<List<MemoData>> = MutableLiveData()
    val personList: LiveData<List<MemoData>>
        get() = _personList

    fun getAll() = disposable.add(repository.getAll(object : DataListener<List<MemoData>> {
        override fun onSuccess(t: List<MemoData>) {
            if(!t.isNullOrEmpty()) {
                _personList.postValue(t)
            } else {
                _personList.postValue(listOf())
            }
        }
    }))

    fun saveMemo(data: MemoData) = disposable.add(repository.saveDataIntoDb(data))
    fun deleteMemo(data: MemoData) = disposable.add(repository.deleteMemo(data))
    fun updateMemo(data: MemoData) = disposable.add(repository.updateMemo(data))
}