package dev.chu.mvvmarchitecture.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * [Dao] : Data Access Object
 * interface 로 구현하며, insert, update, delete 같이 테이블(모델 객체)에서 수행 할 표준 작업을 정의한다.
 */
@Dao
interface CatImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cats: List<Model>)

    @Query("SELECT DISTINCT * FROM $TableName")
    fun getCats(): LiveData<List<Model>>

    @Query("DELETE FROM $TableName")
    fun deleteAll()
}