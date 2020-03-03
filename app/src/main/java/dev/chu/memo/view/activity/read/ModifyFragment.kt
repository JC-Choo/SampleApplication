package dev.chu.memo.view.activity.read

import android.content.DialogInterface
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
import dev.chu.memo.databinding.FragmentModifyBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.confirmDialog
import dev.chu.memo.etc.extension.showToast
import dev.chu.memo.view.adapter.ImageAdapter
import dev.chu.memo.view_model.RoomViewModel

class ModifyFragment : BaseFragment<FragmentModifyBinding>() {
    @LayoutRes
    override fun layoutRes(): Int = R.layout.fragment_modify

    companion object {
        fun newInstance(memoId: Int) = ModifyFragment().apply {
            arguments = Bundle().apply {
                putInt(Const.ARGS.MEMO_ID, memoId)
            }
        }
    }

    private val adapter by lazy { ImageAdapter(mutableListOf()) }
    private lateinit var roomVM: RoomViewModel
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
        binding.modifyFlRvImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.modifyFlRvImage.adapter = adapter
    }

    private fun observeViewModel() {
        roomVM.memo.observe(this, Observer {
            binding.modifyFlEtTitle.setText(it.title)
            binding.modifyFlEtContent.setText(it.content)

//            roomVM.title.value = it.title
//            roomVM.content.value = it.content

            if(!it.imageUrls.isNullOrEmpty()) {
                adapter.setItems(it.imageUrls!!)
                roomVM.setAllImageUrl(it.imageUrls!!)
            }
        })

        roomVM.isUpdate.observe(this, Observer {
            if(it) {
                activity?.finish()
                roomVM.isUpdate.value = false
                showToast(R.string.save_memo)
            }
        })
    }

    fun onClickFinish() {
        if (!binding.modifyFlEtTitle.text.isNullOrEmpty()) {
            context?.confirmDialog(
                getString(R.string.back_memo),
                DialogInterface.OnClickListener { _, _ -> activity?.finish() },
                negativeTextResId = R.string.cancel
            )
        } else activity?.finish()
    }
}