package dev.chu.memo.ui.recycler_multi_viewtype.etc

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {
    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }
}