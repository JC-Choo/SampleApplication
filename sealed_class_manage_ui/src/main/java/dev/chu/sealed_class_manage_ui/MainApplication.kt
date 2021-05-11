package dev.chu.sealed_class_manage_ui

import android.app.Application

class MainApplication : Application() {

    companion object {
        private var instance: MainApplication? = null
        fun get(): MainApplication {
            if (instance == null) {
                synchronized(MainApplication::class) {
                    if (instance == null) {
                        instance = MainApplication()
                    }
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