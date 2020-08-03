package dev.chu.memo.ui.bottom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import dev.chu.memo.R
import dev.chu.memo.base.BaseFragment
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.FragmentProfileBinding
import dev.chu.memo.etc.extension.TAG

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.fragment_profile

    companion object {
        fun newInstance(count: Int) = ProfileFragment().apply {
            arguments = bundleOf(
                Const.ARGS.COUNT to count
            )
        }
    }

    override fun setView(view: View, savedInstanceState: Bundle?, arguments: Bundle?) {
        Log.i(TAG, "setView")

        val result = " Profile "+arguments?.getInt(Const.ARGS.COUNT, 0)
        binding.profileTv.text = result
    }
}