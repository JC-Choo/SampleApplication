package dev.chu.memo.ui.rv_simple

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.BR

open class BaseViewHolder<VDB: ViewDataBinding>(val binding: VDB) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Any) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}