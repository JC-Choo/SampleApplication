package dev.chu.memo.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH: BaseViewHolder<*>>(val items: MutableList<T>) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    open fun setNewItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    open fun addItems(newItems: List<T>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    open fun addItem(item: T) {
        items.add(item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}