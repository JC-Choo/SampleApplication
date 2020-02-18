package dev.chu.memo.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dev.chu.memo.data.local.Bookmark
import dev.chu.memo.data.local.BookmarkDB
import dev.chu.memo.data.local.BookmarkDao

class MainRepository(application: Application) {

    private val bookmarkDao: BookmarkDao by lazy {
        val db = BookmarkDB.getInstance(application)!!
        db.getBookmarkDao()
    }

    private val bookmarks: LiveData<List<Bookmark>> by lazy { bookmarkDao.getAll() }

    fun getAllBookmarks(): LiveData<List<Bookmark>> = bookmarks
    fun insert(bookmark: Bookmark) = bookmarkDao.insert(bookmark)
    fun delete(bookmark: Bookmark) = bookmarkDao.delete(bookmark)
    fun delete(userId: String) = bookmarkDao.deleteById(userId)
}