package dev.chu.rv_drag_to_reorder.swipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import dev.chu.rv_drag_to_reorder.databinding.ActivityMainBinding
import dev.chu.rv_drag_to_reorder.databinding.ActivityRvBinding
import dev.chu.rv_drag_to_reorder.utils.getList

class SwipeToDismissActivity : AppCompatActivity() {
    private var mItemTouchHelper: ItemTouchHelper? = null
    lateinit var swipeToDismissAdapter: SwipeToDismissAdapter
    private lateinit var binding: ActivityRvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerview()
        if (::swipeToDismissAdapter.isInitialized) {
            swipeToDismissAdapter.setNewItems(getList())
        }

    }

    private fun setUpRecyclerview() {
        swipeToDismissAdapter = SwipeToDismissAdapter {

        }

        binding.list.adapter = swipeToDismissAdapter
        val callback: ItemTouchHelper.Callback = SwipeHelperCallback(swipeToDismissAdapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper?.attachToRecyclerView(binding.list)
    }

}