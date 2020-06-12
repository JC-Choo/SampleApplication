package dev.chu.memo.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.chu.memo.R
import dev.chu.memo.databinding.BottomSheetBinding

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false)
        binding.lifecycleOwner = this
        return binding.root

//        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }
}