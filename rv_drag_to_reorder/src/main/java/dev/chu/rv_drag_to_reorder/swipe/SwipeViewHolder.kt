package dev.chu.rv_drag_to_reorder.swipe

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_drag_to_reorder.ItemModel
import dev.chu.rv_drag_to_reorder.databinding.ItemReorderBinding

class SwipeViewHolder(
    private val binding: ItemReorderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemModel) {
        itemView.apply {
            binding.tvAdapter.text = item.title
            binding.imReorder.visibility = View.GONE
        }
    }
}