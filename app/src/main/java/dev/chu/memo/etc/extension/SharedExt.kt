package dev.chu.memo.etc.extension

import android.content.SharedPreferences
import android.preference.PreferenceManager
import dev.chu.memo.GlobalApplication

// region For SharedPreference
fun getPref(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getInstance())

fun getPrefString(key: String, default: String): String = getPref().getString(key, default)!!

fun getPrefStringOrNull(key: String): String? = returnIfContain(key) { getPrefString(key, "") }

fun getPrefStringSet(key: String, default: Set<String>): Set<String> = getPref().getStringSet(key, default)!!

fun getPrefStringSetOrNull(key: String): Set<String>? = returnIfContain(key) { getPrefStringSet(key, setOf()) }

fun getPrefInt(key: String, default: Int): Int = getPref().getInt(key, default)

fun getPrefIntOrNull(key: String): Int? = returnIfContain(key) { getPrefInt(key, 0) }

fun getPrefLong(key: String, default: Long): Long = getPref().getLong(key, default)

fun getPrefLongOrNull(key: String): Long? = returnIfContain(key) { getPrefLong(key, 0) }

fun getPrefFloat(key: String, default: Float): Float = getPref().getFloat(key, default)

fun getPrefFloatOrNull(key: String): Float? = returnIfContain(key) { getPrefFloat(key, 1.toFloat()) }

fun getPrefBoolean(key: String, default: Boolean): Boolean = getPref().getBoolean(key, default)

fun getPrefBooleanOrNull(key: String): Boolean? = returnIfContain(key) { getPrefBoolean(key, true) }

private inline fun <T> returnIfContain(key: String, returnIf: () -> T): T? {
    return if (getPref().contains(key)) {
        returnIf()
    } else {
        null
    }
}

fun setPrefString(key: String, value: String?) {
    applyPref { putString(key, value) }
}

fun setPrefStringSet(key: String, value: Set<String>) {
    applyPref { putStringSet(key, value) }
}

fun setPrefInt(key: String, value: Int) {
    applyPref { putInt(key, value) }
}

fun setPrefLong(key: String, value: Long) {
    applyPref { putLong(key, value) }
}

fun setPrefFloat(key: String, value: Float) {
    applyPref { putFloat(key, value) }
}

fun setPrefBoolean(key: String, value: Boolean) {
    applyPref { putBoolean(key, value) }
}

fun clearPref() {
    applyPref { clear() }
}

fun removePref(key: String) {
    applyPref { remove(key) }
}

fun setPrefMaps(
    stringMap: Map<String, String>? = null,
    stringSetMap: Map<String, Set<String>>? = null,
    intMap: Map<String, Int>? = null,
    longMap: Map<String, Long>? = null,
    floatMap: Map<String, Float>? = null,
    booleanMap: Map<String, Boolean>? = null
) {

    applyPref {
        stringMap?.keys?.forEach { putString(it, stringMap.getValue(it)) }
        stringSetMap?.keys?.forEach { putStringSet(it, stringSetMap.getValue(it)) }
        intMap?.keys?.forEach { putInt(it, intMap.getValue(it)) }
        longMap?.keys?.forEach { putLong(it, longMap.getValue(it)) }
        floatMap?.keys?.forEach { putFloat(it, floatMap.getValue(it)) }
        booleanMap?.keys?.forEach { putBoolean(it, booleanMap.getValue(it)) }
    }
}

private inline fun applyPref(setting: SharedPreferences.Editor.() -> Unit) {
    getPref().edit().run {
        setting()
        apply()
    }
}
// endregion