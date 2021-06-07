package dev.chu.extensions

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

val Any.TAG: String
    get() = this::class.java.simpleName ?: this.toString()

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.toast(@StringRes redId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, redId, duration).show()
}

fun Context.getColorById(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)
fun Context.getDrawableById(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)
fun Context.getScreenSize(): Pair<Int, Int> {
    val width = resources.displayMetrics.widthPixels
    val height = resources.displayMetrics.heightPixels
    return Pair(width, height)
}

fun View.click(block: (View) -> Unit) {
    this.setOnClickListener(block)
}

fun Context.hasPermissions(vararg permissions: String) = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.copyToClipboard(content: String) {
    val clipboardManager = ContextCompat.getSystemService(this, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("clipboard", content)
    clipboardManager?.setPrimaryClip(clip) ?: run {
        toast("ClipboardManager is null")
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.isResolvable(intent: Intent) = intent.resolveActivity(packageManager) != null

fun Context.view(uri: Uri, onAppMissing: () -> Unit) {
    val intent = Intent(Intent.ACTION_VIEW, uri)
    if (!isResolvable(intent)) {
        onAppMissing.invoke()
        return
    }
    startActivity(intent)
}

val isoFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA)
fun Date.toISOFormat() : String = isoFormatter.format(this)

fun Any?.isNull() = this == null
