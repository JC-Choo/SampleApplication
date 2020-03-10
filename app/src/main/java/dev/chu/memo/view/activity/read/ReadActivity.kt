package dev.chu.memo.view.activity.read

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.ActivityReadBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.replaceFragment
import dev.chu.memo.view_model.RoomViewModel

class ReadActivity : BaseActivity<ActivityReadBinding>(), ReadFragment.FCallback {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_read

    private val roomVM by lazy { ViewModelProvider(this)[RoomViewModel::class.java] }

    private var memoId: Int = 0
//    private var data: MemoData? = null

    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this

//        setActionBarHome(binding.includeToolbar.toolbar, R.drawable.arrow_back_white)
//        binding.includeToolbar.toolbarTv.text = ""
//        binding.includeToolbar.toolbarTvDelete.visibility = View.VISIBLE
//        binding.includeToolbar.toolbarTvDelete.setOnClickListener {
//            data?.let {
//                confirmDialog(R.string.do_you_delete_this_memo, DialogInterface.OnClickListener { dialog, which ->
//                    roomVM.deleteMemo(it, false)
//                    finish()
//                })
//            } ?: run {
//                showToast("결과 잘못 불러옴")
//            }
//        }
//        binding.includeToolbar.toolbarTvEtc.text = getString(R.string.modify)
//        binding.includeToolbar.toolbarTvEtc.setOnClickListener {
//            replaceFragment(binding.readFl.id, ModifyFragment.newInstance(memoId), ModifyFragment.TAG)
//            binding.includeToolbar.toolbarTvDelete.visibility = View.GONE
//            binding.includeToolbar.toolbarTvEtc.isEnabled = false
//        }

        initData()
//        observerViewModel()

        replaceFragment(binding.readFl.id, ReadFragment.newInstance(memoId), ReadFragment.TAG)
    }

    private fun initData() {
        memoId = intent.getIntExtra(Const.EXTRA.MEMO_ID, 0)

//        roomVM.getDataById(memoId)
    }

//    private fun observerViewModel() {
//        roomVM.memo.observe(this, Observer {
//            data = it
//        })
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
//        android.R.id.home -> {
//            finish()
//            true
//        }
//        else -> super.onOptionsItemSelected(item)
//    }

    override fun onClickEtc() {
        replaceFragment(binding.readFl.id, ModifyFragment.newInstance(memoId), ModifyFragment.TAG)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}