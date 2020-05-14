package dev.chu.memo.ui.recycler_multi_viewtype.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.BR
import dev.chu.memo.ui.recycler_multi_viewtype.ViewType

class ViewTypeHolder(
    private val binding: ViewDataBinding,
    private val onItemActionListener: OnItemActionListener? = null
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindItem(item: ViewType<*>) {
        binding.setVariable(BR.model, item.data())
        if (item.isUserInteractionEnabled()) {
            binding.setVariable(BR.position, adapterPosition)
            binding.setVariable(BR.actionItemListener, onItemActionListener)
        }
        binding.executePendingBindings()
    }
}