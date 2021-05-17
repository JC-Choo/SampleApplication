package dev.chu.rv_search_view_hilt

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import dev.chu.rv_search_view_hilt.utils.isNight

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()

        // Get UI mode and set
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}