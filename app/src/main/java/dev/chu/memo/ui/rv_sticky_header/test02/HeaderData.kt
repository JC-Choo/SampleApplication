package dev.chu.memo.ui.rv_sticky_header.test02

import androidx.annotation.LayoutRes

interface StickyMainData {

}

interface HeaderData : StickyMainData {
    @get:LayoutRes
    val headerLayout: Int
    val headerType: Int
}