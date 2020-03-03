package dev.chu.memo.view.activity.read

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.memo.R
import dev.chu.memo.base.BaseFragment
import dev.chu.memo.common.Const
import dev.chu.memo.databinding.FragmentReadBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.view.adapter.ImageAdapter
import dev.chu.memo.view_model.RoomViewModel

class ReadFragment : BaseFragment<FragmentReadBinding>() {
    @LayoutRes
    override fun layoutRes(): Int = R.layout.fragment_read

    companion object {
        fun newInstance(memoId: Int) = ReadFragment().apply {
            arguments = Bundle().apply {
                putInt(Const.ARGS.MEMO_ID, memoId)
            }
        }
    }

    private lateinit var roomVM: RoomViewModel
    private val adapter by lazy { ImageAdapter(mutableListOf()) }
    private var memoId: Int = 0

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        Log.i(TAG, "setView")

        binding.fragment = this

        roomVM = activity?.let {
            ViewModelProvider(this)[RoomViewModel::class.java]
        } ?: throw Exception("Activity is null")

        arguments?.let {
            memoId = it.getInt(Const.ARGS.MEMO_ID, 0)
        }

        roomVM.getDataById(memoId)

        setRecyclerView()
        observeViewModel()
    }

    private fun setRecyclerView() {
        binding.readFlRvImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.readFlRvImage.adapter = adapter
    }

    private fun observeViewModel() {
        roomVM.memo.observe(this, Observer {
            binding.readFlTvTitle.text = it.title
            binding.readFlTvContent.text = it.content

            if(!it.imageUrls.isNullOrEmpty())
                adapter.setItems(it.imageUrls!!)
        })
    }
}