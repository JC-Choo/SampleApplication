package dev.chu.memo.ui.memo_read

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.ActivityReadBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.onBackPressedFragment
import dev.chu.memo.etc.extension.replaceFragment

class ReadActivity : BaseActivity<ActivityReadBinding>(),
    ReadFragment.FCallback {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_read

    private var memoId: Int = 0

    override fun initView(savedInstanceState: Bundle?) {
        Log.i(TAG, "initView")

        memoId = intent.getIntExtra(Const.EXTRA.MEMO_ID, 0)
        replaceFragment(binding.readFl.id,
            ReadFragment.newInstance(
                memoId
            ), ReadFragment.TAG)
    }

    override fun onClickEtc() {
        replaceFragment(binding.readFl.id,
            ModifyFragment.newInstance(
                memoId
            ), ModifyFragment.TAG)
    }

    override fun onBackPressed() {
        onBackPressedFragment()
    }
}