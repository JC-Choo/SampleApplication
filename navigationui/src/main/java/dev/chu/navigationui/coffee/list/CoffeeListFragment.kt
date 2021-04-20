package dev.chu.navigationui.coffee.list

import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dev.chu.extensions.click
import dev.chu.navigationui.R
import dev.chu.navigationui.base.BaseFragment
import dev.chu.navigationui.coffee.CoffeeViewModelFactory
import dev.chu.navigationui.databinding.FragmentCoffeeBinding
import dev.chu.navigationui.storage.SnackDatabase

/**
 * 추적하고 있는 커피의 현재 리스트를 보여주는 리싸이클러뷰를 포함한 프레그먼트
 */
class CoffeeListFragment : BaseFragment<FragmentCoffeeBinding>(
    R.layout.fragment_coffee
) {

    private val viewModel by viewModels<CoffeeListViewModel> {
        val coffeeDao = SnackDatabase.getDatabase(requireContext()).coffeeDao()
        CoffeeViewModelFactory(coffeeDao)
    }

    private val adapter by lazy {
        CoffeeListAdapter(onEdit = { coffee ->
            findNavController().navigate(
                CoffeeListFragmentDirections.actionCoffeeListToCoffeeEntryDialogFragment(
                    coffee.id
                )
            )
        }, onDelete = { coffee ->
            NotificationManagerCompat.from(requireContext()).cancel(coffee.id.toInt())
            viewModel.delete(coffee)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.coffeeList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.recyclerView.adapter = adapter
        binding.fab.click { findNavController().navigate(CoffeeListFragmentDirections.actionCoffeeListToCoffeeEntryDialogFragment()) }
    }
}