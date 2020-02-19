package dev.chu.memo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 5)
abstract class MemoDB : RoomDatabase() {
    abstract fun getBookmarkDao(): MemoDao

    companion object {
        private var instance: MemoDB? = null
        fun getInstance(context: Context): MemoDB? {
            if(instance == null) {
                synchronized(MemoDB::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        MemoDB::class.java, "bookmark.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance
        }
    }

    fun destroyInstance() {
        instance = null
    }
}