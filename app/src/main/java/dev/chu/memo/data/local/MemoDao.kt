package dev.chu.memo.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import dev.chu.memo.common.Const
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MemoDao {
    @Query("SELECT * FROM ${Const.TABLE_NAME_1} ORDER BY created ASC")
    fun getAll(): LiveData<List<MemoData>>

    @Query("SELECT * FROM ${Const.TABLE_NAME_1} ORDER BY created ASC")
    fun findAll(): DataSource.Factory<Int, MemoData>

    @Query("DELETE FROM ${Const.TABLE_NAME_1}")
    fun deleteAll()



    @Query("SELECT * FROM ${Const.TABLE_NAME_1}")
    fun getAllRecords(): Single<List<MemoData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemoData(data: MemoData): Completable

    @Delete
    fun deleteMemoData(data: MemoData): Completable

    @Update
    fun updateMemoData(data: MemoData): Completable
}