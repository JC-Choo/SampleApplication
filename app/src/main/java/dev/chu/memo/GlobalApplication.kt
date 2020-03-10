package dev.chu.memo

import android.app.Application

class GlobalApplication : Application() {
    companion object {
        private var instance: GlobalApplication? = null
        fun getInstance(): GlobalApplication {
            if (instance == null) {
                instance = GlobalApplication()
            }

            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}