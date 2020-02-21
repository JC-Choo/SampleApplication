package dev.chu.memo.view

import android.content.Intent
import android.util.Log
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.ActivityReadBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.setActionBarHome
import dev.chu.memo.view.adapter.ImageAdapter
import dev.chu.memo.view_model.RoomViewModel

class ReadActivity : BaseActivity<ActivityReadBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_read

    private val roomVM by lazy { ViewModelProvider(this)[RoomViewModel::class.java] }
    private val adapter by lazy { ImageAdapter(mutableListOf()) }
    private var memoId: Int = 0

    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this

        setActionBarHome(binding.includeToolbar.toolbar, R.drawable.arrow_back_white)
        binding.includeToolbar.toolbarTv.text = ""
        binding.includeToolbar.toolbarTvEtc.text = getString(R.string.modify)
        binding.includeToolbar.toolbarTvEtc.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java).apply {

            })
        }

        memoId = intent.getIntExtra(Const.EXTRA.MEMO_ID, 0)

        setRecyclerView()
        observeViewModel()
    }

    private fun setRecyclerView() {
        binding.readRvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.readRvImage.adapter = adapter
    }

    private fun observeViewModel() {
        roomVM.getDataById(memoId)
        roomVM.memo.observe(this, Observer {
            binding.readTvTitle.text = it.title
            binding.readTvContent.text = it.content

            if(!it.imageUrls.isNullOrEmpty())
                adapter.setItems(it.imageUrls!!)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}