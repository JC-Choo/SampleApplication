package dev.chu.memo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bookmark::class], version = 5)
abstract class BookmarkDB : RoomDatabase() {
    abstract fun getBookmarkDao(): BookmarkDao

    companion object {
        private var instance: BookmarkDB? = null
        fun getInstance(context: Context): BookmarkDB? {
            if(instance == null) {
                synchronized(BookmarkDB::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        BookmarkDB::class.java, "bookmark.db")
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