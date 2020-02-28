package dev.chu.memo.view.activity.read

import android.util.Log
import android.view.MenuItem
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.ActivityReadBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.replaceFragment
import dev.chu.memo.etc.extension.setActionBarHome

class ReadActivity : BaseActivity<ActivityReadBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_read

    private var memoId: Int = 0

    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this

        setActionBarHome(binding.includeToolbar.toolbar, R.drawable.arrow_back_white)
        binding.includeToolbar.toolbarTv.text = ""
        binding.includeToolbar.toolbarTvEtc.text = getString(R.string.modify)
        binding.includeToolbar.toolbarTvEtc.setOnClickListener {
            replaceFragment(binding.readFl.id, ModifyFragment.newInstance(memoId), ModifyFragment.TAG)
        }

        memoId = intent.getIntExtra(Const.EXTRA.MEMO_ID, 0)

        replaceFragment(binding.readFl.id, ReadFragment.newInstance(memoId), ReadFragment.TAG)

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