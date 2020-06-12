package dev.chu.memo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface FavTvShowsDao {

    @Query("SELECT * FROM FavTvShows")
    fun getAllShows(): Flow<List<FavTvShows>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setAllShows(shows: List<FavTvShows>)


    @Query("SELECT * FROM FavTvShows WHERE name=:name")
    fun getShow(name: String): Flow<FavTvShows>
    @ExperimentalCoroutinesApi
    fun getShowDistinctUntilChanged(name: String) = getShow(name).distinctUntilChanged()
}