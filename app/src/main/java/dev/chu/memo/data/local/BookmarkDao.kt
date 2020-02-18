package dev.chu.memo.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM Bookmark ORDER BY created ASC")
    fun getAll(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM Bookmark ORDER BY created ASC")
    fun findAll(): DataSource.Factory<Int, Bookmark>

    @Query("SELECT * FROM Bookmark WHERE login = :login")
    fun getBookmarkByName(login: String): LiveData<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg bookmark: Bookmark)

    @Update
    fun update(vararg bookmark: Bookmark)

    @Delete
    fun delete(bookmark: Bookmark)

    @Query("DELETE FROM Bookmark")
    fun deleteAll()

    @Query("DELETE FROM Bookmark WHERE user_id = :userId")
    fun deleteById(userId: String)
}