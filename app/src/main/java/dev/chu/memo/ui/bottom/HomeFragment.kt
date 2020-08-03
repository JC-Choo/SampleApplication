package dev.chu.memo.ui.bottom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import dev.chu.memo.R
import dev.chu.memo.base.BaseFragment
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.FragmentHomeBinding
import dev.chu.memo.etc.extension.TAG

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.fragment_home

    companion object {
        fun newInstance(count: Int) = HomeFragment().apply {
            arguments = bundleOf(
                Const.ARGS.COUNT to count
            )
        }
    }

    override fun setView(view: View, savedInstanceState: Bundle?, arguments: Bundle?) {
        Log.i(TAG, "initView")

        val result = "Home "+arguments?.getInt(Const.ARGS.COUNT, 0)
        binding.homeTv.text = result
    }
}