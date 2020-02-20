package dev.chu.memo.view

import android.util.Log
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityReadBinding

class ReadActivity : BaseActivity<ActivityReadBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_read

    private val TAG = ReadActivity::class.java.simpleName

    override fun initView() {
        Log.i(TAG, "initView")
    }

}