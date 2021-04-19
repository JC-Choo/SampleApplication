package dev.chu.navigationui.storage

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.chu.navigationui.model.Donut

/**
 * DAO(Data Access Object)는 기본 데이터베이스 로부터/에서 데이터를 되찾고 저장하는데 사용된다.
 * API 는 직접적으로 사용하지 않는다; 대신, 호출자는 해당 DAO 내에 호출하는 Repository 를 사용한다.
 */
@Dao
interface DonutDao {
    @Query("SELECT * FROM donut")
    fun getAll(): LiveData<List<Donut>>

    @Query("SELECT * FROM donut WHERE id = :id")
    suspend fun get(id: Long): Donut

    @Insert
    suspend fun insert(donut: Donut): Long

    @Delete
    suspend fun delete(donut: Donut)

    @Update
    suspend fun update(donut: Donut)
}