package dev.chu.memo.view.activity.memo

import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.ActivityMemoBinding
import dev.chu.memo.etc.extension.*
import dev.chu.memo.view_model.RoomViewModel

class MemoActivity  : BaseActivity<ActivityMemoBinding>() {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_memo

    private val roomVM by lazy { ViewModelProvider(this)[RoomViewModel::class.java] }

    // region lifeCycle
    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this
        binding.roomVM = roomVM

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

        roomVM.getAll()
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
