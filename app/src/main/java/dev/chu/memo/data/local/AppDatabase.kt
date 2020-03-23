package dev.chu.memo.data.local

import androidx.room.*
import dev.chu.memo.GlobalApplication
import dev.chu.memo.entity.GithubRepo
import java.util.*

@Database(
    entities = [GithubRepo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun get(): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        GlobalApplication.getInstance().applicationContext, AppDatabase::class.java, "app_db.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}

class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
