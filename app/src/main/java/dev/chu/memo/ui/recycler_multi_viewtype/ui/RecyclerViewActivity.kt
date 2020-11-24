package dev.chu.memo.ui.recycler_multi_viewtype.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity2
import dev.chu.memo.databinding.ActivityRecyclerViewBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.ui.recycler_multi_viewtype.ViewType
import dev.chu.memo.ui.recycler_multi_viewtype.etc.lazyN
import org.koin.android.ext.android.inject

// 참고 : https://proandroiddev.com/using-a-generic-recyclerview-adapter-for-all-the-lists-in-your-android-application-6724501a9d

class RecyclerViewActivity : BaseActivity2<ActivityRecyclerViewBinding>(R.layout.activity_recycler_view) {

    private val recyclerViewVM by inject<RecyclerViewViewModel>()
    private val adapter by lazyN { ViewTypeAdapter<ViewType<*>>(onItemActionListener = recyclerViewVM) }

    override fun initView(savedInstanceState: Bundle?) {
        Log.i(TAG, "initView")

        binding.vm = recyclerViewVM
        setUpAdapter()
        initObservers()
    }

    private fun setUpAdapter() {
        binding.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter.setList(recyclerViewVM.getList())
        binding.rvList.adapter = adapter
    }

    private fun initObservers() {
        recyclerViewVM.insertEvent.observe(this, Observer {
            adapter.insertElement(it.second, it.first)
        })
        recyclerViewVM.removeItemEvent.observe(this, Observer {
            adapter.removeElement(it)
        })
        recyclerViewVM.updateEvent.observe(this, Observer {
            adapter.updateElement(it.second, it.first)
        })
    }

}