package dev.chu.sealed_class_manage_ui.data

import android.content.Context
import dev.chu.sealed_class_manage_ui.R

class ResourceManager(
    private val context: Context
) {

    val errorMsg: String
        get() = context.getString(R.string.error_msg)
}