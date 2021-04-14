package dev.chu.extensions

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

// https://betterprogramming.pub/5-more-kotlin-extensions-for-android-developers-3857b1f16407

// region [Show or Hide the Keyboard]
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: RuntimeException) {
        e.printStackTrace()
    }
    return false
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}
// endregion

// region [Color Conversion]
fun String.hexToRGB(): Triple<String, String, String> {
    var name = this
    if (!name.startsWith("#")) {
        name = "#$this"
    }
    val color = Color.parseColor(name)
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)

    return Triple(red.toString(), green.toString(), blue.toString())
}

fun Int.colorToHexString(): String {
    return String.format("#%06X", -0x1 and this).replace("#FF", "#")
}
// endregion

fun Context.dpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun isAndroid24More() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
fun isAndroid26More() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O