package dev.chu.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

val Any.TAG: String
    get() = this::class.java.simpleName ?: this.toString()

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun Context.toast(@StringRes redId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, redId, duration).show()

fun Context.getColorById(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)
fun Context.getDrawableById(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)
fun Context.getScreenSize(): Pair<Int, Int> {
    val width = resources.displayMetrics.widthPixels
    val height = resources.displayMetrics.heightPixels
    return Pair(width, height)
}