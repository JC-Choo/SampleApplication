package dev.chu.rv_drag_to_reorder.reorder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_drag_to_reorder.ItemModel
import dev.chu.rv_drag_to_reorder.databinding.ItemReorderBinding
import dev.chu.rv_drag_to_reorder.utils.ItemTouchHelperAdapter
import dev.chu.rv_drag_to_reorder.utils.OnStartDragListener
import java.util.*
import kotlin.collections.ArrayList

class ReorderAdapter(
    private val dragStartListener: OnStartDragListener,
    private val reordered: () -> Unit
) : RecyclerView.Adapter<ReorderViewHolder>(), ItemTouchHelperAdapter {

    private var items: ArrayList<ItemModel> = ArrayList()

    fun setData(array: List<ItemModel>) {
        items.clear()
        items.addAll(array)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReorderViewHolder {
        val binding = ItemReorderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReorderViewHolder(binding, reordered, dragStartListener)
    }

    override fun onBindViewHolder(holder: ReorderViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        TODO("Not yet implemented")
    }
}