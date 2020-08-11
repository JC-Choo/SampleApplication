package dev.chu.memo.base.recycler_view

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import dev.chu.basemodule.recycler_view.BaseItemModel

class BaseDiffUtilItemCallback<T : BaseItemModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}