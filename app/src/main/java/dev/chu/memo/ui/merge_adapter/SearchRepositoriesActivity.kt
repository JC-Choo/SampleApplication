package dev.chu.memo.ui.merge_adapter

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.data.remote.RepoSearchResult
import dev.chu.memo.databinding.ActivitySearchRepositoriesBinding
import dev.chu.memo.etc.extension.TAG
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

// https://medium.com/androiddevelopers/merge-adapters-sequentially-with-mergeadapter-294d2942127a

class SearchRepositoriesActivity : BaseActivity<ActivitySearchRepositoriesBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_search_repositories

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }

    private val viewModel by viewModel<SearchRepositoriesViewModel>()
    private val adapter by inject<ReposAdapter>()
    private lateinit var loadStateAdapter: ReposLoadStateAdapter

    override fun initView(savedInstanceState: Bundle?) {
        setRecyclerView()
        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchRepo(query)
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchEtSearchRepo.text.trim().toString())
    }

    private fun setRecyclerView() {
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.searchRv.addItemDecoration(decoration)
        setUpScrollListener()
    }

    private fun setUpScrollListener() {
        val layoutManager = binding.searchRv.layoutManager as LinearLayoutManager
        binding.searchRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    private fun initAdapter() {
        loadStateAdapter = ReposLoadStateAdapter { viewModel.retry() }
        binding.searchRv.adapter?.setHasStableIds(true)
//        binding.searchRv.adapter = MergeAdapter(
//            adapter,
//            loadStateAdapter
//        )

        viewModel.repoResult.observe(this, Observer { result ->
            when (result) {
                is RepoSearchResult.Success -> {
                    showEmptyList(result.data.isEmpty())
                    adapter.submitList(result.data)
                }
                is RepoSearchResult.Error -> {
                    Toast.makeText(
                        this,
                        "\uD83D\uDE28 Wooops $result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        viewModel.repoLoadStatus.observe(this, Observer { loadState ->
            Log.d(TAG, "load state $loadState")
            loadStateAdapter.loadState = loadState
        })
    }

    private fun initSearch(query: String) {
        binding.searchEtSearchRepo.setText(query)

        binding.searchEtSearchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchEtSearchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.searchTvEmptyList.visibility = View.VISIBLE
            binding.searchRv.visibility = View.GONE
        } else {
            binding.searchTvEmptyList.visibility = View.GONE
            binding.searchRv.visibility = View.VISIBLE
        }
    }

    private fun updateRepoListFromInput() {
        binding.searchEtSearchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                binding.searchRv.scrollToPosition(0)
                viewModel.searchRepo(it.toString())
                adapter.submitList(null)
            }
        }
    }
}