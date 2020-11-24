package dev.chu.memo.ui.bottom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import dev.chu.memo.R
import dev.chu.memo.base.BaseFragment
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.FragmentNavigationBinding
import dev.chu.memo.etc.extension.TAG

class NotificationFragment : BaseFragment<FragmentNavigationBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.fragment_navigation

    companion object {
        fun newInstance(count: Int) = NotificationFragment().apply {
            arguments = bundleOf(
                Const.ARGS.COUNT to count
            )
        }
    }

    override fun setView(view: View, savedInstanceState: Bundle?, arguments: Bundle?) {
        Log.i(TAG, "setView")

        val result = " Navigation "+arguments?.getInt(Const.ARGS.COUNT, 0)
        binding.navigationTv.text = result
    }
}