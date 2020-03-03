package dev.chu.memo.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseAndroidViewModel
import dev.chu.memo.data.local.ImageData
import dev.chu.memo.data.local.MemoData
import dev.chu.memo.data.repository.RoomRepository
import dev.chu.memo.etc.listener.DataListener
import java.util.*

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
    fun deleteMemo(data: MemoData) = disposable.add(repository.deleteMemo(data, object : DataListener<List<MemoData>> {
        override fun onSuccess(t: List<MemoData>) {
            if(!t.isNullOrEmpty()) {
                _memoList.postValue(t.reversed())
            } else {
                _memoList.postValue(listOf())
            }
        }
    }))
    fun updateMemo(data: MemoData) = disposable.add(repository.updateMemo(data))





    var isSave: MutableLiveData<Boolean> = MutableLiveData(false)
    var isUpdate: MutableLiveData<Boolean> = MutableLiveData(false)
    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()
    private var _listImageUrls: MutableList<ImageData> = mutableListOf()
    fun clearImageUrls() {
        _listImageUrls.clear()
    }
    fun addImageUrl(data: ImageData) {
        _listImageUrls.add(data)
    }
    fun setAllImageUrl(list: List<ImageData>) {
        _listImageUrls.clear()
        _listImageUrls.addAll(list)
    }
    fun saveMemo() {
        disposable.add(repository.saveDataIntoDb(MemoData(title = title.value, content = content.value, imageUrls = _listImageUrls, created = Date())))
        isSave.value = true
    }
    fun updateMemo() {
        disposable.add(repository.updateMemo(MemoData(title = title.value, content = content.value, imageUrls = _listImageUrls, created = Date())))
        isUpdate.value = true
    }
}