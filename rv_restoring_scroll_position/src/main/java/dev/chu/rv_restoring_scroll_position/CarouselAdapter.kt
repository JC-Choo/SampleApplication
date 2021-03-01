package dev.chu.rv_restoring_scroll_position

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_restoring_scroll_position.databinding.ItemCardBinding

class CarouselAdapter : ListAdapter<Int, CarouselAdapter.CardViewHolder>(DiffUtilCarouselItem()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CardViewHolder(
        private val binding: ItemCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(index: Int) {
            binding.txtCard.text = index.toString()
            when (index) {
                1 -> binding.txtCard.setBackgroundColor(Color.parseColor("#FFF70D"))
                2 -> binding.txtCard.setBackgroundColor(Color.parseColor("#EBD20C"))
                3 -> binding.txtCard.setBackgroundColor(Color.parseColor("#FFCC00"))
                4 -> binding.txtCard.setBackgroundColor(Color.parseColor("#E8AA0C"))
                5 -> binding.txtCard.setBackgroundColor(Color.parseColor("#FFA70F"))
                6 -> binding.txtCard.setBackgroundColor(Color.parseColor("#FFA721"))
                7 -> binding.txtCard.setBackgroundColor(Color.parseColor("#E87B13"))
                8 -> binding.txtCard.setBackgroundColor(Color.parseColor("#FF6912"))
                else -> binding.txtCard.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    private class DiffUtilCarouselItem : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}