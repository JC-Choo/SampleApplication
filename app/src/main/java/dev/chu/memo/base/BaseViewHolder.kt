package dev.chu.memo.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<out T : ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view) {
    val binding: T = DataBindingUtil.bind(view)!!
    open fun bind(item : Any) {}
}