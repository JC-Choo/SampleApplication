package dev.chu.memo.view

import android.content.Intent
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.ActivityMainBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.setActionBarHome
import dev.chu.memo.view.adapter.MainAdapter
import dev.chu.memo.view_model.RoomViewModel

/**
 * 메인 화면에서 하는 일
 *
 * 1. 저장된 메모(제목, 내용, 이미지 첫번째꺼) 보여주기
 * 2. "추가" 클릭 시 메모 쓰는 화면(AddActivity) 로 이동
 * 3. 메모 클릭 시 메모 확인하는 화면(ReadActivity) 이동
 */

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_main

    private val roomVM by lazy { ViewModelProvider(this)[RoomViewModel::class.java] }
    private val adapter by lazy { MainAdapter(mutableListOf(), this) }

    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this

        setActionBarHome(binding.includeToolbar.toolbar, null)
        binding.includeToolbar.toolbarTv.text = getString(R.string.memo)
        binding.includeToolbar.toolbarTvEtc.text = getString(R.string.add)
        binding.includeToolbar.toolbarTvEtc.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java).apply {
                putExtra(Const.EXTRA.MEMO, getString(R.string.write))
            })
        }

        setRecyclerView()
        observeViewModel()
    }

    private fun setRecyclerView() {
        binding.mainRv.adapter = adapter
    }

    private fun observeViewModel() {
        roomVM.getAll()
        roomVM.memoList.observe(this, Observer {
            if(!it.isNullOrEmpty()) {
                adapter.setItems(it)
            }
        })
    }
}
