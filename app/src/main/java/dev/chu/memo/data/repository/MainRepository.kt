package dev.chu.memo.data.repository

import android.app.Application
import android.util.Log
import dev.chu.memo.data.local.MemoDao
import dev.chu.memo.data.local.MemoData
import dev.chu.memo.data.local.MemoDatabase
import dev.chu.memo.etc.listener.DataListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainRepository(application: Application) {
    private val TAG = MainRepository::class.java.simpleName

    private val memoDao: MemoDao by lazy {
        val db = MemoDatabase.getInstance(application)
        db.getMemoDao()
    }

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
        memoDao.getDataById(memoId, )

    fun deleteMemo(data: MemoData) =
        memoDao.deleteMemoData(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "deleteMemo onSuccess")
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