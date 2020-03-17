package dev.chu.memo

import android.app.Application
import dev.chu.memo.di_koin.myDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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

        startKoin {
            androidContext(this@GlobalApplication)
            modules(myDiModule)
        }
    }
}