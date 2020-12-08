package dev.chu.memo.etc.shared

import android.content.Context

class Prefs (context: Context) {
    private val pref = context.getSharedPreferences("TEST", Context.MODE_PRIVATE)

    private val APP_PREF_INT_EXAMPLE = "intExamplePref"

    var intExamplePref: Int
        get() = pref.getInt(APP_PREF_INT_EXAMPLE, -1)
        set(value) = pref.edit().putInt(APP_PREF_INT_EXAMPLE, value).apply()
}