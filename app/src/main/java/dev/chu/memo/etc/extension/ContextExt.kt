package dev.chu.memo.etc.extension

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.chu.memo.R

fun Context.getDrawableById(@DrawableRes res: Int): Drawable =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) getDrawable(res)!! else resources.getDrawable(
        res
    )

fun Context.getColorById(@ColorRes res: Int): Int =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) getColor(res) else resources.getColor(res)

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes messageRes: Int) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
}

fun Context.showAlert(
    title: String? = null,
    message: String,
    positiveMsg: String = getString(R.string.ok),
    positiveBtn: DialogInterface.OnClickListener? = null,
    negativeMsg: String? = null,
    negativeBtn: DialogInterface.OnClickListener? = null
) = AlertDialog.Builder(this).apply {
    if (title != null) setTitle(title)
    setMessage(message)
    setPositiveButton(positiveMsg, positiveBtn)
    if (negativeMsg != null) setNegativeButton(negativeMsg, negativeBtn)
}.create()