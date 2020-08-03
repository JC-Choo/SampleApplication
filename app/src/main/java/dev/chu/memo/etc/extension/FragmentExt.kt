package dev.chu.memo.etc.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import dev.chu.memo.base.BaseActivity

fun Fragment.replaceFragmentInFragment(@IdRes frameId: Int, fragment: Fragment) {
    childFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun Fragment.addFragmentInFragment(@IdRes frameId: Int, fragment: Fragment) {
    childFragmentManager.transact {
        add(frameId, fragment)
    }
}

fun Fragment.showKeyBoard() {
    context?.showKeyboard()
}

fun Fragment.hideKeyboard() {
    context?.hideKeyboard()
}

fun Fragment.showToast(msg: CharSequence, isLong: Boolean = false) {
    (activity as BaseActivity<*>).showToast(msg, isLong)
}

fun Fragment.showToast(msgId: Int, isLong: Boolean = false) {
    (activity as? BaseActivity<*>)?.showToast(msgId, isLong)
}