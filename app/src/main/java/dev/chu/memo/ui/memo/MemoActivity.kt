package dev.chu.memo.ui.memo

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.ActivityMemoBinding
import dev.chu.memo.etc.extension.*
import dev.chu.memo.ui.memo_add.AddActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MemoActivity  : BaseActivity<ActivityMemoBinding>() {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_memo

    // region lifeCycle
    override fun initView(savedInstanceState: Bundle?) {
        Log.i(TAG, "initView")

        binding.activity = this
        binding.roomVM = getViewModel()

        setActionBarHome(binding.includeToolbar.toolbar, null)

        binding.includeToolbar.toolbarTv.text = getString(R.string.memo)
        binding.includeToolbar.toolbarTvEtc.text = getString(R.string.add)
        binding.includeToolbar.toolbarTvEtc.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java).apply {
                putExtra(Const.EXTRA.IS_WRITING_MEMO, false)
            })
        }

        val title = getPrefString(Const.PREF.MEMO_TITLE, "")
        val content = getPrefString(Const.PREF.MEMO_CONTENT, "")

        if(title != "" || content != "") {
            confirmDialog(R.string.Please_check_writing_memo, DialogInterface.OnClickListener { _, _ ->
                startActivity(Intent(this, AddActivity::class.java).apply {
                    putExtra(Const.EXTRA.IS_WRITING_MEMO, true)
                })
            }, DialogInterface.OnClickListener { _, _ ->
                showToast(R.string.delete_writing_memo)
                removePref(Const.PREF.MEMO_TITLE)
                removePref(Const.PREF.MEMO_CONTENT)
            })
        }

        // https://levelup.gitconnected.com/animate-android-activities-transition-bf7f89a74b35
        overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
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

        getViewModel<MemoViewModel>().getAll()
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
}
