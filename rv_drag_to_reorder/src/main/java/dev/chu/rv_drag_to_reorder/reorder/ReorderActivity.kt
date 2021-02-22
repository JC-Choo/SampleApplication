package dev.chu.rv_drag_to_reorder.reorder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_drag_to_reorder.databinding.ActivityRvBinding
import dev.chu.rv_drag_to_reorder.utils.OnStartDragListener
import dev.chu.rv_drag_to_reorder.utils.TAG
import dev.chu.rv_drag_to_reorder.utils.getList

class ReorderActivity : AppCompatActivity(), OnStartDragListener {

    private var mItemTouchHelper: ItemTouchHelper? = null
    lateinit var adapter: ReorderAdapter
    private val binding: ActivityRvBinding by lazy {
        ActivityRvBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecyclerview()

        if (::adapter.isInitialized) {
            adapter.setData(getList())
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        viewHolder?.let {
            mItemTouchHelper?.startDrag(it)
        }
    }

    private fun setUpRecyclerview() {
        adapter = ReorderAdapter(dragStartListener = this) {
            // Execute functionality after reorder
            Log.v(TAG, "reorder completed")
        }
        binding.list.adapter = adapter

        val callback: ItemTouchHelper.Callback = ReorderHelperCallback(adapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper?.attachToRecyclerView(binding.list)
    }
}