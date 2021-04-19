package dev.chu.navigationui.donut.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.navigationui.databinding.ItemDonutBinding
import dev.chu.navigationui.model.Donut

class DonutDiffCallback : DiffUtil.ItemCallback<Donut>() {
    override fun areItemsTheSame(oldItem: Donut, newItem: Donut): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Donut, newItem: Donut): Boolean {
        return oldItem == newItem
    }
}

class DonutListAdapter(
    private var onEdit: (Donut) -> Unit,
    private var onDelete: (Donut) -> Unit
) : ListAdapter<Donut, DonutListAdapter.DonutListViewHolder>(DonutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonutListViewHolder {
        val binding = ItemDonutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = DonutListViewHolder(binding)
        binding.root.setOnClickListener { onEdit(getItem(viewHolder.adapterPosition)) }
        binding.deleteButton.setOnClickListener { onDelete(getItem(viewHolder.adapterPosition)) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: DonutListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DonutListViewHolder(
        private val binding: ItemDonutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Donut) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}