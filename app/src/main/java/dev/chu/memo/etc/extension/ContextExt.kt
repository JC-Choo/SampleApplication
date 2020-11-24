package dev.chu.memo.etc.extension

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//fun Context.getDrawableById(@DrawableRes res: Int): Drawable =
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) getDrawable(res)!! else resources.getDrawable(res)
//
//fun Context.getColorById(@ColorRes res: Int): Int =
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) getColor(res) else resources.getColor(res)


@ColorInt
fun Context.getColorById(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)
fun Context.getDrawableById(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)
fun Context.getDimension(@DimenRes resourceId: Int) = resources.getDimension(resourceId)
val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, msg, duration).show()

fun Context.getPxFromDp(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()


//fun Context.showToast(message: String, isLong: Boolean = false) {
//    Toast.makeText(this, message, if(isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
//}
//
//fun Context.showToast(@StringRes messageRes: Int, isLong: Boolean = false) {
//    showToast(getString(messageRes, isLong))
//}

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


fun Context.callPhone(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    startActivity(intent)
}

fun Context.showPlayStore() {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
    startActivity(intent)
}

fun Context.showKeyboard() {
    GlobalScope.launch {
        delay(100)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    }
}

fun Context.hideKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
        InputMethodManager.HIDE_IMPLICIT_ONLY,
        0
    )
}