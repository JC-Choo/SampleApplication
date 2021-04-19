package dev.chu.navigationui.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.chu.navigationui.model.Coffee
import dev.chu.navigationui.model.Donut

/**
 * 도넷에 대한 정보가 저장되어 있는 기본 데이터베이스
 */
@Database(entities = [Donut::class, Coffee::class], version = 1)
abstract class SnackDatabase : RoomDatabase() {
    abstract fun donutDao(): DonutDao
    abstract fun coffeeDao(): CoffeeDao

    companion object {
        @Volatile
        private var INSTANCE: SnackDatabase? = null
        fun getDatabase(context: Context): SnackDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, SnackDatabase::class.java, "snack_database")
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}