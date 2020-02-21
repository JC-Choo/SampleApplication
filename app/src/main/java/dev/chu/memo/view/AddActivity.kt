package dev.chu.memo.view

import android.content.DialogInterface
import android.util.Log
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityWriteBinding
import dev.chu.memo.etc.extension.confirmDialog
import dev.chu.memo.etc.extension.setActionBarHome
import dev.chu.memo.etc.extension.showToast
import dev.chu.memo.view.adapter.ImageAdapter
import dev.chu.memo.view_model.WriteViewModel

class AddActivity : BaseActivity<ActivityWriteBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_write

    private val TAG = AddActivity::class.java.simpleName

    private val writeVm by lazy { ViewModelProvider(this).get(WriteViewModel::class.java) }
    private val adapter by lazy { ImageAdapter(mutableListOf()) }

    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this
        binding.viewModel = writeVm

        setActionBarHome(binding.includeToolbar.toolbar, R.drawable.arrow_back_white)
        binding.includeToolbar.toolbarTv.text = ""
        binding.includeToolbar.toolbarTvEtc.text = getString(R.string.picture)
        binding.includeToolbar.toolbarTvEtc.setOnClickListener {
            showToast("사진 및 갤러리")
        }

        setRecyclerView()
        observeViewModel()
    }

    private fun setRecyclerView() {
        binding.writeRvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.writeRvImage.adapter = adapter
    }

    private fun observeViewModel() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(!binding.writeEtTitle.text.isNullOrEmpty()) {
            confirmDialog(getString(R.string.back_memo), DialogInterface.OnClickListener { _, _ -> finish() }, negativeTextResId = R.string.cancel)
        } else finish()
    }
}