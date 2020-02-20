package dev.chu.memo.view

import android.content.Intent
import android.util.Log
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_main

    private val TAG = MainActivity::class.java.simpleName

    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this

        startActivity(Intent(this, WriteActivity::class.java))
    }
}
