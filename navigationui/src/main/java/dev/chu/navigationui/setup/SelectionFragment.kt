package dev.chu.navigationui.setup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dev.chu.extensions.click
import dev.chu.navigationui.R
import dev.chu.navigationui.base.BaseFragment
import dev.chu.navigationui.databinding.FragmentSelectionBinding

/**
 * 해당 프레그먼트는 커피 추적 기능을 끄고 킨다.
 */
class SelectionFragment: BaseFragment<FragmentSelectionBinding>(
    R.layout.fragment_selection
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.click {
            val coffeeEnabled = binding.checkBox.isSelected
            // TODO enable coffee tracker
            findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToDonutList())
        }
    }
}