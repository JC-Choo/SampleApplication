package dev.chu.navigationui.coffee.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.chu.extensions.click
import dev.chu.navigationui.Notifier
import dev.chu.navigationui.R
import dev.chu.navigationui.coffee.CoffeeViewModelFactory
import dev.chu.navigationui.databinding.FragmentCoffeeEntryBinding
import dev.chu.navigationui.model.Coffee
import dev.chu.navigationui.storage.SnackDatabase

/**
 * 해당 다이얼로그는 사용자가 새 엔트리를 만들 뿐만 아니라 기존의 것을 업데이트 하야 커피에 대한 정보를 등록한다.
 */
class CoffeeEntryDialogFragment : BottomSheetDialogFragment() {

    private enum class EditingState {
        NEW_COFFEE, EXISTING_COFFEE
    }

    private var _binding: FragmentCoffeeEntryBinding? = null
    private val binding: FragmentCoffeeEntryBinding get() = _binding!!

    private val viewModel by viewModels<CoffeeEntryViewModel> {
        val coffeeDao = SnackDatabase.getDatabase(requireContext()).coffeeDao()
        CoffeeViewModelFactory(coffeeDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoffeeEntryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var coffee: Coffee? = null
        val args: CoffeeEntryDialogFragmentArgs by navArgs()
        val editingState =
            if (args.itemId > 0) EditingState.EXISTING_COFFEE else EditingState.NEW_COFFEE

        // itemId가 >= 0 일 경우 기존 아이템을 편집한다.
        if (editingState == EditingState.EXISTING_COFFEE) {
            // 인자에 전달된 id에 맞는 기존 아이템의 편집을 요청한다.
            // 해당 아이템을 검색하고 세부 정보로 UI를 채운다.
            viewModel.get(args.itemId).observe(viewLifecycleOwner) { coffeeItem ->
                viewModel.item.value = coffeeItem
                coffee = coffeeItem
            }
        }

        binding.doneButton.click {
            val context = requireContext().applicationContext
            val navController = findNavController()

            viewModel.addData(
                coffee?.id ?: 0,
                binding.name.text.toString(),
                binding.description.text.toString(),
                binding.ratingBar.rating.toInt()
            ) { actualId ->
                val arg = CoffeeEntryDialogFragmentArgs(actualId).toBundle()
                val pendingIntent = navController
                    .createDeepLink()
                    .setDestination(R.id.coffeeEntryDialogFragment)
                    .setArguments(arg)
                    .createPendingIntent()

                Notifier.postNotification(actualId, context, pendingIntent)
            }
            dismiss()
        }

        binding.cancelButton.click { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}