package dev.chu.memo.ui.memo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseViewModel
import dev.chu.memo.data.local.ImageData
import dev.chu.memo.data.local.MemoData
import dev.chu.memo.data.repository.RoomRepository
import dev.chu.memo.etc.listener.DataListener
import java.util.*

class MemoViewModel(private val repository: RoomRepository) : BaseViewModel() {
    private var _memoList: MutableLiveData<List<MemoData>> = MutableLiveData(arrayListOf())
    val memoList: LiveData<List<MemoData>>
        get() = _memoList

    private var _memo: MutableLiveData<MemoData> = MutableLiveData()
    val memo: LiveData<MemoData>
        get() = _memo

    var memoId: MutableLiveData<Int> = MutableLiveData(-1)
    var isSave: MutableLiveData<Boolean> = MutableLiveData(false)
    var isUpdate: MutableLiveData<Boolean> = MutableLiveData(false)
    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()

    fun getAll() = addDisposable(repository.getAll(object : DataListener<List<MemoData>> {
        override fun onSuccess(t: List<MemoData>) {
            if(!t.isNullOrEmpty()) {
                _memoList.postValue(t.reversed())
            } else {
                _memoList.postValue(listOf())
            }
        }

        override fun onFail(e: Throwable) {

        }
    }))

    fun getDataById(memoId: Int) {
        this.memoId.value = memoId
        addDisposable(repository.getDataById(memoId, object : DataListener<MemoData> {
            override fun onSuccess(t: MemoData) {
                _memo.postValue(t)
            }

            override fun onFail(e: Throwable) {

            }
        }))
    }

    fun deleteMemo(data: MemoData, isGetAll: Boolean = true) = addDisposable(if(isGetAll) {
        repository.deleteMemo(data, object : DataListener<List<MemoData>> {
            override fun onSuccess(t: List<MemoData>) {
                if(!t.isNullOrEmpty()) {
                    _memoList.postValue(t.reversed())
                } else {
                    _memoList.postValue(listOf())
                }
            }

            override fun onFail(e: Throwable) {

            }
        })
    } else {
        repository.deleteMemo(data)
    })

    fun saveMemo(listImageUrls: MutableList<ImageData>) {
        addDisposable(repository.saveDataIntoDb(MemoData(title = title.value, content = content.value, imageUrls = listImageUrls, created = Date())))
        isSave.value = true
    }
    fun updateMemo(listImageUrls: MutableList<ImageData>) {
        addDisposable(repository.updateMemo(MemoData(memo_id = memoId.value!!, title = title.value, content = content.value, imageUrls = listImageUrls, created = Date())))
        isUpdate.value = true
    }
}