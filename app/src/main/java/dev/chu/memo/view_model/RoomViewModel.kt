package dev.chu.memo.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseAndroidViewModel
import dev.chu.memo.data.local.MemoData
import dev.chu.memo.data.repository.RoomRepository
import dev.chu.memo.etc.listener.DataListener

class RoomViewModel(application: Application) : BaseAndroidViewModel(application) {
    private val repository by lazy { RoomRepository(application) }

    private var _memoList: MutableLiveData<List<MemoData>> = MutableLiveData()
    val memoList: LiveData<List<MemoData>>
        get() = _memoList

    private var _memo: MutableLiveData<MemoData> = MutableLiveData()
    val memo: LiveData<MemoData>
        get() = _memo

    fun getAll() = disposable.add(repository.getAll(object : DataListener<List<MemoData>> {
        override fun onSuccess(t: List<MemoData>) {
            if(!t.isNullOrEmpty()) {
                _memoList.postValue(t.reversed())
            } else {
                _memoList.postValue(listOf())
            }
        }
    }))

    fun getDataById(memberId: Int) = disposable.add(repository.getDataById(memberId, object : DataListener<MemoData> {
        override fun onSuccess(t: MemoData) {
            _memo.postValue(t)
        }
    }))

    fun saveMemo(data: MemoData) = disposable.add(repository.saveDataIntoDb(data))
    fun deleteMemo(data: MemoData) = disposable.add(repository.deleteMemo(data))
    fun updateMemo(data: MemoData) = disposable.add(repository.updateMemo(data))
}