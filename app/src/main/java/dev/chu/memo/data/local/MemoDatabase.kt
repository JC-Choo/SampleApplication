package dev.chu.memo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

private const val DB_NAME = "Memo.db"
private const val DB_VERSION = 1

@Database(
    entities = [MemoData::class, ImageData::class],
    version = DB_VERSION
)
@TypeConverters(ConverterDate::class, ConverterList::class)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun getMemoDao(): MemoDao

    companion object {
        @Volatile
        private var instance: MemoDatabase? = null
        fun getInstance(context: Context): MemoDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabaseInstance(context).also {
                    instance = it
                }
            }

        private fun buildDatabaseInstance(context: Context) =
            Room.databaseBuilder(context, MemoDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    fun destroyInstance() {
        instance = null
    }
}