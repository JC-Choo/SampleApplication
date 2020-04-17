package dev.chu.memo.ui

import android.content.Intent
import android.util.Log
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityMainBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.ui.bottom.BottomNavigationActivity
import dev.chu.memo.ui.fav_tv_shows.FavTvShowsActivity
import dev.chu.memo.ui.map.CoronaActivity
import dev.chu.memo.ui.memo.MemoActivity
import dev.chu.memo.ui.merge_adapter.SearchRepositoriesActivity
import dev.chu.memo.ui.motion_layout.MotionActivity
import dev.chu.memo.ui.mvi.MviActivity
import dev.chu.memo.ui.rv_coroutine.UserActivity
import dev.chu.memo.ui.rx_activity.repos.GithubReposActivity

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
        startActivity(Intent(this, CoronaActivity::class.java))
    }

    fun onClickBottomNavigationActivity() {
        startActivity(Intent(this, BottomNavigationActivity::class.java))
    }

    fun onClickGithubReposActivity() {
        startActivity(Intent(this, GithubReposActivity::class.java))
    }

    fun onClickUserActivity() {
        startActivity(Intent(this, UserActivity::class.java))
    }

    fun onClickMviActivity() {
        startActivity(Intent(this, MviActivity::class.java))
    }

    fun onClickMotionActivity() {
        startActivity(Intent(this, MotionActivity::class.java))
    }

    fun onClickSearchRepositoriesActivity() {
        startActivity(Intent(this, SearchRepositoriesActivity::class.java))
    }

    fun onClickFavTvShowsActivity() {
        startActivity(Intent(this, FavTvShowsActivity::class.java))
    }

    fun onClickFavTvSpeechActivity() {
        startActivity(Intent(this, SpeechActivity::class.java))
    }
}
