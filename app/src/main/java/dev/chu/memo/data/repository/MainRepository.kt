package dev.chu.memo.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dev.chu.memo.data.local.Memo
import dev.chu.memo.data.local.MemoDB
import dev.chu.memo.data.local.MemoDao

class MainRepository(application: Application) {

    private val memoDao: MemoDao by lazy {
        val db = MemoDB.getInstance(application)!!
        db.getBookmarkDao()
    }

    private val bookmarks: LiveData<List<Memo>> by lazy { memoDao.getAll() }

    fun getAllBookmarks(): LiveData<List<Memo>> = bookmarks
    fun insert(memo: Memo) = memoDao.insert(memo)
    fun delete(memo: Memo) = memoDao.delete(memo)
//    fun delete(userId: String) = memoDao.deleteById(userId)
}