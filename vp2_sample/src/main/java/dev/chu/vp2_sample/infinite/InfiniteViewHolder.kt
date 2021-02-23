package dev.chu.vp2_sample.infinite

import androidx.recyclerview.widget.RecyclerView
import dev.chu.vp2_sample.databinding.ItemFullLengthBinding

class InfiniteViewHolder(
    private val binding: ItemFullLengthBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        binding.txtLabel.text = item
    }
}