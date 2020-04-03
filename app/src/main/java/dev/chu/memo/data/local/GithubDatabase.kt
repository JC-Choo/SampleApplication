package dev.chu.memo.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.chu.memo.GlobalApplication
import dev.chu.memo.entity.GithubRepo

private const val DB_NAME = "app_db.db"
private const val DB_VERSION = 1

@Database(
    entities = [GithubRepo::class],
    version = DB_VERSION
)
@TypeConverters(ConverterDate::class)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    companion object {
        private var INSTANCE: GithubDatabase? = null

        fun get(): GithubDatabase {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        GlobalApplication.getInstance().applicationContext, GithubDatabase::class.java, DB_NAME
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}