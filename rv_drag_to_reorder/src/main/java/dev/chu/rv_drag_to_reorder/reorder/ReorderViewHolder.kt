package dev.chu.rv_drag_to_reorder.reorder

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_drag_to_reorder.ItemModel
import dev.chu.rv_drag_to_reorder.databinding.ItemReorderBinding
import dev.chu.rv_drag_to_reorder.utils.ItemTouchHelperViewHolder
import dev.chu.rv_drag_to_reorder.utils.OnStartDragListener
import dev.chu.rv_drag_to_reorder.utils.TAG

class ReorderViewHolder(
    private val binding: ItemReorderBinding,
    private val reordered: () -> Unit,
    private val dragStartListener: OnStartDragListener? = null
) : RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

    @SuppressLint("ClickableViewAccessibility")
    fun bind(item: ItemModel) {
        with(binding) {
            tvAdapter.text = item.title
            imReorder.setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragStartListener?.onStartDrag(this@ReorderViewHolder)
                }
                false
            }
        }
    }

    override fun onItemSelected() {
        Log.i(TAG, "onItemSelected")
    }

    override fun onItemClear() {
        Log.i(TAG, "onItemClear")
        reordered
    }
}