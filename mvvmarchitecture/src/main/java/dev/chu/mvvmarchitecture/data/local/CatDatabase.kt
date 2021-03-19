package dev.chu.mvvmarchitecture.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * table 에 대한 Room Database 만들기
 * [Database] : entities, version 을 정의한다.
 * abstract class 이므로, 어떤 삽입 또는 삭제 작업을 여기서 하지 않는다.
 * 모든 작업은 Repository class 에서 처리한다.
 * Dao class 의 함수를 호출하기 위해 CatImageDao 에 대한 abstract fun 을 만든다.
 *
 * 주요 동기는 삽입 또는 삭제 작업을 수행하기 위해 Repository class에서 사용할 수 있는 database 의 instance 를 생성하는 것.
 * callback 을 통해 새 DB 가 생성될때 마다 이전 DB를 삭제한다.
 */
@Database(entities = [Model::class], version = 1)
abstract class CatDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "Cat.db"

        @Volatile
        var instance: CatDatabase? = null
        fun getInstance(context: Context): CatDatabase {
            if (instance == null) {
                synchronized(CatDatabase::class) {
                    instance =
                        Room.databaseBuilder(context, CatDatabase::class.java, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build()
                }
            }
            return instance!!
        }

        private val callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                CoroutineScope(Dispatchers.IO).launch {
                    instance?.catImageDao()?.deleteAll()
                }
            }
        }
    }

    abstract fun catImageDao(): CatImageDao
}