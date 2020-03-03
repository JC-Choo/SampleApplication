package dev.chu.memo.etc.extension

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}