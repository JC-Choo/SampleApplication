package kr.co.mustit.check_language

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

class MainApplication : Application() {

    companion object {
        private var instance: MainApplication? = null
        fun get(): MainApplication {
            if (instance == null) {
                instance = MainApplication()
            }

            return instance!!
        }
    }

    private fun getPref() = PreferenceManager.getDefaultSharedPreferences(get())

    private inline fun applyPref(setting: SharedPreferences.Editor.() -> Unit) {
        getPref().edit().run {
            setting()
            apply()
        }
    }

    fun setPrefString(key: String, value: String?) {
        applyPref { putString(key, value) }
    }

    fun getPrefString(key: String, default: String): String = getPref().getString(key, default)!!

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}