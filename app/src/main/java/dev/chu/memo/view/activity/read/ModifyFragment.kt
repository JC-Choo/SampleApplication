package dev.chu.memo.view.activity.read

import android.os.Bundle
import android.util.Log
import android.view.View
import dev.chu.memo.R
import dev.chu.memo.base.BaseFragment
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.FragmentModifyBinding
import dev.chu.memo.etc.extension.TAG

class ModifyFragment : BaseFragment<FragmentModifyBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_modify

    companion object {
        fun newInstance(memoId: Int) = ModifyFragment().apply {
            arguments = Bundle().apply {
                putInt(Const.ARGS.MEMO_ID, memoId)
            }
        }
    }

    private var memoId: Int = 0

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        Log.i(TAG, "setView")

        binding.fragment = this

        arguments?.let {
            memoId = it.getInt(Const.ARGS.MEMO_ID, 0)
        }
    }
}