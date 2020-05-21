package dev.chu.memo.etc.click

import android.view.View
import androidx.databinding.BindingAdapter

interface ClickBinding {
    @BindingAdapter("onClick")
    fun setOnClickListener(view: View, onClickListener: View.OnClickListener)
}