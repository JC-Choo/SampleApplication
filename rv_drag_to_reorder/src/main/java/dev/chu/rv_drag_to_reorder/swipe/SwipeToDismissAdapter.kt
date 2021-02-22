package dev.chu.rv_drag_to_reorder.swipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_drag_to_reorder.ItemModel
import dev.chu.rv_drag_to_reorder.databinding.ItemReorderBinding
import dev.chu.rv_drag_to_reorder.utils.ItemTouchHelperAdapter

class SwipeToDismissAdapter(
    private val onSwiped: () -> Unit
): RecyclerView.Adapter<SwipeViewHolder>(), ItemTouchHelperAdapter {

    private val items = mutableListOf<ItemModel>()
    fun setNewItems(newItems: List<ItemModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeViewHolder {
        val binding = ItemReorderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean = false

    override fun onItemDismiss(position: Int) {
        items.removeAt(position);
        notifyItemRemoved(position);
        onSwiped()
    }
}