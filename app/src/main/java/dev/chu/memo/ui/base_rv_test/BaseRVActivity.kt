package dev.chu.memo.ui.base_rv_test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.memo.base.recycler_view.BaseDiffUtilItemCallback
import dev.chu.basemodule.recycler_view.BaseRvAdapter
import dev.chu.basemodule.recycler_view.ListItemOnClickListener
import dev.chu.memo.BR
import dev.chu.memo.R
import dev.chu.memo.databinding.ActivityBaseRvBinding
import dev.chu.memo.etc.extension.showToast

// https://medium.com/dev-genius/creating-a-recyclerview-adapter-which-can-be-used-with-any-data-model-and-any-item-row-view-with-1144ca58f0be

class BaseRVActivity : AppCompatActivity(), ListItemOnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityBaseRvBinding>(this, R.layout.activity_base_rv)
        val itemModelList = mutableListOf<ItemModel>()
        for(i in 0..29) {
            val itemModel = ItemModel(
                itemId = i.toString()
            )
            itemModel.name = i.toString()
            itemModelList.add(itemModel)
        }

        val baseRecyclerViewAdapter = BaseRvAdapter<ItemModel>(
            R.layout.item_base_rv,
            BR.rowMainListObj,
            this,
            BaseDiffUtilItemCallback()
        )

        binding.baseRvRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@BaseRVActivity)
            adapter = baseRecyclerViewAdapter
        }

        baseRecyclerViewAdapter.submitList(itemModelList)
    }

    override fun onItemClick(adapterPosition: Int, view: View?) {
        showToast("onItemClick $adapterPosition")
    }

    override fun onItemLongClick(adapterPosition: Int, view: View?): Boolean {
        showToast("onItemLongClick $adapterPosition", true)
        return true
    }
}