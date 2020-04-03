package dev.chu.memo.ui.merge_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.databinding.ItemReposLoadStateHeaderViewBinding

class ReposLoadStateViewHolder(
    private val binding: ItemReposLoadStateHeaderViewBinding,
    retry: () -> Unit
): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repos_load_state_header_view, parent, false)
//            val binding = ReposLoadStateHeaderViewItemBinding.bind(view)
            val binding = DataBindingUtil.bind<ItemReposLoadStateHeaderViewBinding>(view)!!
            return ReposLoadStateViewHolder(binding, retry)
        }
    }

    init {
        binding.retryButton.also {
            it.setOnClickListener { retry() }
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
//        binding.progressBar.visibility = toVisibility(loadState == LoadState.Loading)
//        binding.retryButton.visibility = toVisibility(loadState != LoadState.Loading)
//        binding.errorMsg.visibility = toVisibility(loadState != LoadState.Loading)
    }

    private fun toVisibility(constraint: Boolean): Int = if (constraint) {
        View.VISIBLE
    } else {
        View.GONE
    }
}