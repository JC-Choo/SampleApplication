package dev.chu.memo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.chu.memo.entity.GithubRepo
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GithubDao {
    @Query("SELECT * FROM githubrepo")
    fun selectAll(): Single<List<GithubRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: GithubRepo): Completable
}
