package dev.chu.navigationui.donut.list

import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dev.chu.navigationui.R
import dev.chu.navigationui.base.BaseFragment
import dev.chu.navigationui.databinding.FragmentDonutListBinding
import dev.chu.navigationui.donut.DonutViewModelFactory
import dev.chu.navigationui.storage.SnackDatabase

/**
 * 현재 추적하고 있는 도넛의 리스트를 보여주는
 * [androidx.recyclerview.widget.RecyclerView] 를 포함한 [androidx.fragment.app.Fragment]
 */
class DonutListFragment : BaseFragment<FragmentDonutListBinding>(R.layout.fragment_donut_list) {

    private lateinit var viewModel: DonutListViewModel
    private val adapter by lazy {
        DonutListAdapter(onEdit = { donut ->
            findNavController().navigate(
                DonutListFragmentDirections.actionDonutListToDonutEntryDialogFragment(donut.id)
            )
        }, onDelete = { donut ->
            NotificationManagerCompat.from(requireContext()).cancel(donut.id.toInt())
            viewModel.delete(donut)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val donutDao = SnackDatabase.getDatabase(requireContext()).donutDao()
        viewModel = ViewModelProvider(this, DonutViewModelFactory(donutDao))
            .get(DonutListViewModel::class.java)

        viewModel.donuts.observe(viewLifecycleOwner) { donuts ->
            adapter.submitList(donuts)
        }

        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener { fabView ->
            fabView.findNavController().navigate(
                DonutListFragmentDirections.actionDonutListToDonutEntryDialogFragment()
            )
        }
    }
}