package dev.chu.memo.view.activity

import android.content.Intent
import android.util.Log
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityMainBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.view.activity.bottom.BottomNavigationActivity
import dev.chu.memo.view.activity.map.MapsActivity
import dev.chu.memo.view.activity.memo.MemoActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_main

    // region lifeCycle
    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
    // endregion

    fun onClickMemo() {
        startActivity(Intent(this, MemoActivity::class.java))
    }

    fun onClickCorona() {
        startActivity(Intent(this, MapsActivity::class.java))
    }

    fun onClickEtc() {
        startActivity(Intent(this, BottomNavigationActivity::class.java))
    }
}
