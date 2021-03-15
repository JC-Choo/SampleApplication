package dev.chu.rv_motion_layout

import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_motion_layout.databinding.ItemFoodBinding

class FoodViewHolder(
    private val binding: ItemFoodBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FoodModel) {
        binding.tvTitle.text = item.title
        binding.tvDescription.text = item.description
        binding.tvCalories.text = item.calories
        binding.tvRate.text = item.rate
        binding.ivFood.setImageResource(item.imgId)
    }
}