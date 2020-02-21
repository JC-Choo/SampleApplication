package dev.chu.memo.data.local

import androidx.room.*
import dev.chu.memo.common.Const
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MemoDao {
    @Query("SELECT * FROM ${Const.TABLE_NAME_1} WHERE memo_id=:memoId")
    fun getDataById(memoId: Int): Single<MemoData>

    @Query("SELECT * FROM ${Const.TABLE_NAME_1}")
    fun getAllRecords(): Single<List<MemoData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemoData(data: MemoData): Completable

    @Delete
    fun deleteMemoData(data: MemoData): Completable

    @Update
    fun updateMemoData(data: MemoData): Completable
}