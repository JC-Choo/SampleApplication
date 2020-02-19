package dev.chu.memo.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM Memo ORDER BY created ASC")
    fun getAll(): LiveData<List<Memo>>

    @Query("SELECT * FROM Memo ORDER BY created ASC")
    fun findAll(): DataSource.Factory<Int, Memo>

//    @Query("SELECT * FROM Memo WHERE login = :login")
//    fun getMemoByName(login: String): LiveData<Memo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg memo: Memo)

    @Update
    fun update(vararg memo: Memo)

    @Delete
    fun delete(memo: Memo)

    @Query("DELETE FROM Memo")
    fun deleteAll()

//    @Query("DELETE FROM Memo WHERE user_id = :userId")
//    fun deleteById(userId: String)
}