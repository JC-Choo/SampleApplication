package dev.chu.navigationui.storage

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.chu.navigationui.model.Coffee

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffee")
    fun getAll(): LiveData<List<Coffee>>

    @Query("SELECT * FROM coffee WHERE id = :id")
    suspend fun get(id: Long): Coffee

    @Insert
    suspend fun insert(coffee: Coffee): Long

    @Delete
    suspend fun delete(coffee: Coffee)

    @Update
    suspend fun update(coffee: Coffee)
}