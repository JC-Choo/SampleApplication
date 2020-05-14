package dev.chu.memo.ui.recycler_multi_viewtype

import androidx.annotation.LayoutRes

interface ViewType<out T> {

    @LayoutRes
    fun layoutId(): Int
    fun data(): T
    fun isUserInteractionEnabled() = true

}
