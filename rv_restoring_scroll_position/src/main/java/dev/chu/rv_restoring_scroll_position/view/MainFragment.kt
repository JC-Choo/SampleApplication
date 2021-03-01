package dev.chu.rv_restoring_scroll_position.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.chu.rv_restoring_scroll_position.ContentAdapter
import dev.chu.rv_restoring_scroll_position.R
import dev.chu.rv_restoring_scroll_position.click
import dev.chu.rv_restoring_scroll_position.databinding.FragmentMainBinding

class MainFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private val contentAdapter by lazy { ContentAdapter() }

    companion object {
        fun newInstance() = MainFragment().apply {
            arguments = bundleOf().apply {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(layoutInflater).apply {
            initComponents(this)
            observeState()
        }.root
    }

    private fun initComponents(binding: FragmentMainBinding) {
        binding.content.adapter = contentAdapter
        binding.openFragment.click {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.container, OtherFragment.newInstance())
                addToBackStack(OtherFragment::class.java.name)
            }.commit()
        }
    }

    private fun observeState() {
        viewModel.content.observe(viewLifecycleOwner) {
            contentAdapter.submitList(it)
        }
    }
}