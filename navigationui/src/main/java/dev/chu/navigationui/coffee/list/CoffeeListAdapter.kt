package dev.chu.navigationui.coffee.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.extensions.click
import dev.chu.navigationui.databinding.ItemCoffeeBinding
import dev.chu.navigationui.model.Coffee

class CoffeeDiffCallback: DiffUtil.ItemCallback<Coffee>() {
    override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem == newItem
    }
}

class CoffeeListAdapter(
    private val onEdit: (Coffee) -> Unit,
    private val onDelete: (Coffee) -> Unit
): ListAdapter<Coffee, CoffeeListAdapter.CoffeeListViewHolder>(CoffeeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoffeeBinding.inflate(inflater, parent, false)
        val viewHolder = CoffeeListViewHolder(binding)
        binding.root.click { onEdit(getItem(viewHolder.adapterPosition)) }
        binding.deleteButton.click { onDelete(getItem(viewHolder.adapterPosition)) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CoffeeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CoffeeListViewHolder(
        private val binding: ItemCoffeeBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Coffee) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}