package dev.chu.memo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

private const val DB_NAME = "FavTvShows.db"
private const val DB_VERSION = 1

@Database(
    entities = [FavTvShows::class],
    version = DB_VERSION
)
@TypeConverters(ConverterDate::class, ConverterList::class)
abstract class FavTvShowsDatabase : RoomDatabase() {
    abstract fun getFavTvShowsDao(): FavTvShowsDao

    companion object {
        @Volatile
        private var instance: FavTvShowsDatabase? = null
        fun getInstance(context: Context): FavTvShowsDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabaseInstance(context).also {
                    instance = it
                }
            }

        private fun buildDatabaseInstance(context: Context) =
            Room.databaseBuilder(context, FavTvShowsDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    fun destroyInstance() {
        instance = null
    }
}