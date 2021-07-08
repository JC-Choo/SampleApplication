package dev.chu.twowaybinding

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapterUtils {
    @JvmStatic
    @BindingAdapter("app:updateVisibility")
    fun updateVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}