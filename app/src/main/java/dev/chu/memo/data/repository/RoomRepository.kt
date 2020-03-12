package dev.chu.memo.data.repository

import android.util.Log
import dev.chu.memo.data.local.MemoDao
import dev.chu.memo.data.local.MemoData
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.listener.DataListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomRepository(private val memoDao: MemoDao) {

    fun saveDataIntoDb(data: MemoData) =
        memoDao.insertMemoData(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "saveDataIntoDb onSuccess")
            }, {
                Log.e(TAG, "saveDataIntoDb onError")
                it.printStackTrace()
            })

    fun getAll(listener: DataListener<List<MemoData>>) =
        memoDao.getAllRecords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "getAll onSuccess")
                listener.onSuccess(it)
            }, {
                Log.e(TAG, "getAll onError")
                it.printStackTrace()
            })

    fun getDataById(memoId: Int, listener: DataListener<MemoData>) =
        memoDao.getDataById(memoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "getDataById onSuccess")
                listener.onSuccess(it)
            }, {
                Log.e(TAG, "getDataById onError")
                it.printStackTrace()
            })

    fun deleteMemo(data: MemoData, listener: DataListener<List<MemoData>>? = null) =
        memoDao.deleteMemoData(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "deleteMemo onSuccess")
                listener?.let { getAll(it) }
            }, {
                Log.e(TAG, "deleteMemo onError")
                it.printStackTrace()
            })

    fun updateMemo(data: MemoData) =
        memoDao.updateMemoData(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "updateMemo onSuccess")
            }, {
                Log.e(TAG, "updateMemo onError")
                it.printStackTrace()
            })
}