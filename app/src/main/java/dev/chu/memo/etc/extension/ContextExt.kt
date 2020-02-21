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

// region Dialog or Message
fun Context.alertDialog(@StringRes messageId: Int, listener: DialogInterface.OnClickListener? = null) {
    alertDialog(getString(messageId), listener)
}

fun Context.alertDialog(
    message: String,
    listener: DialogInterface.OnClickListener? = null,
    positiveTextResId: Int? = null,
    positiveTextString: String? = null
) {
    val positiveText =
        if (positiveTextResId != null) getString(positiveTextResId)
        else positiveTextString ?: getString(android.R.string.ok)

    AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage(message)
        .setPositiveButton(positiveText, listener)
        .show()
}

fun Context.confirmDialog(
    @StringRes messageId: Int,
    okListener: DialogInterface.OnClickListener? = null,
    cancelListener: DialogInterface.OnClickListener? = null
) {
    confirmDialog(getString(messageId), okListener, cancelListener)
}

fun Context.confirmDialog(
    @StringRes messageId: Int,
    okListener: DialogInterface.OnClickListener? = null,
    cancelListener: DialogInterface.OnClickListener? = null,
    positiveTextResId: Int? = null,
    negativeTextResId: Int? = null
) {
    confirmDialog(
        getString(messageId),
        okListener,
        cancelListener,
        positiveTextResId,
        negativeTextResId
    )
}

fun Context.confirmDialog(
    message: String,
    okListener: DialogInterface.OnClickListener? = null,
    cancelListener: DialogInterface.OnClickListener? = null,
    positiveTextResId: Int? = null,
    negativeTextResId: Int? = null
) {
    AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage(message)
        .setPositiveButton(positiveTextResId ?: android.R.string.ok, okListener)
        .setNegativeButton(negativeTextResId ?: android.R.string.cancel, cancelListener)
        .show()
}

fun Context.confirmDialogCustom(
    @StringRes messageId: Int, okText: String, noText: String,
    okListener: DialogInterface.OnClickListener? = null,
    cancelListener: DialogInterface.OnClickListener? = null
) {
    confirmDialogCustom(getString(messageId), okText, noText, okListener, cancelListener)
}

fun Context.confirmDialogCustom(
    message: String, okText: String, noText: String,
    okListener: DialogInterface.OnClickListener? = null,
    cancelListener: DialogInterface.OnClickListener? = null
) {
    AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage(message)
        .setPositiveButton(okText, okListener)
        .setNegativeButton(noText, cancelListener)
        .create().show()
}
// endregion