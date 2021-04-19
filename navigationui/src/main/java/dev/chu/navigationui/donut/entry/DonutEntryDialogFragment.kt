package dev.chu.navigationui.donut.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.chu.navigationui.Notifier
import dev.chu.navigationui.R
import dev.chu.navigationui.databinding.DialogDonutEntryBinding
import dev.chu.navigationui.donut.DonutViewModelFactory
import dev.chu.navigationui.model.Donut
import dev.chu.navigationui.storage.SnackDatabase

/**
 * 해당 다이얼로그는 사용자가 새 엔트리(항목)을 만들거나 기존 엔트리를 업데이트하여 도넛에 대한 정보를 입력할 수 있다.
 */
class DonutEntryDialogFragment : BottomSheetDialogFragment() {

    private enum class EditingState {
        NEW_DONUT,
        EXISTING_DONUT
    }

    private var _binding: DialogDonutEntryBinding? = null
    private val binding: DialogDonutEntryBinding get() = _binding!!

    private val viewModel: DonutEntryViewModel by viewModels {
        val donutDao = SnackDatabase.getDatabase(requireContext()).donutDao()
        DonutViewModelFactory(donutDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDonutEntryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var donut: Donut? = null
        val args: DonutEntryDialogFragmentArgs by navArgs()
        val editingState =
            if (args.itemId > 0) {
                EditingState.EXISTING_DONUT
            } else {
                EditingState.NEW_DONUT
            }

        // If we arrived here with an itemId of >= 0, then we are editing an existing item
        if (editingState == EditingState.EXISTING_DONUT) {
            // Request to edit an existing item, whose id was passed in as an argument.
            // Retrieve that item and populate the UI with its details
            viewModel.get(args.itemId).observe(viewLifecycleOwner) { donutItem ->
                binding.apply {
                    name.setText(donutItem.name)
                    description.setText(donutItem.description)
                    ratingBar.rating = donutItem.rating.toFloat()
                }
                donut = donutItem
            }
        }

        // When the user clicks the Done button, use the data here to either update
        // an existing item or create a new one
        binding.doneButton.setOnClickListener {
            // Grab these now since the Fragment may go away before the setupNotification
            // lambda below is called
            val context = requireContext().applicationContext
            val navController = findNavController()

            viewModel.addData(
                donut?.id ?: 0,
                binding.name.text.toString(),
                binding.description.text.toString(),
                binding.ratingBar.rating.toInt()
            ) { actualId ->
                val arg = DonutEntryDialogFragmentArgs(actualId).toBundle()
                val pendingIntent = navController
                    .createDeepLink()
                    .setDestination(R.id.donutEntryDialog)
                    .setArguments(arg)
                    .createPendingIntent()

                Notifier.postNotification(actualId, context, pendingIntent)
            }
            dismiss()
        }

        // User clicked the Cancel button; just exit the dialog without saving the data
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}