package dev.chu.mvvmarchitecture

import android.app.Application

class MainApplication : Application() {

    companion object {
        @Volatile
        private var instance: MainApplication? = null

        fun getInstance(): MainApplication {
            if (instance == null) {
                synchronized(MainApplication::class) {
                    instance = MainApplication()
                }
            }

            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}