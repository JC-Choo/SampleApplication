package dev.chu.rv_infinite_scroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_infinite_scroll.databinding.ItemFeatureBinding

class FeatureDiffCallback: DiffUtil.ItemCallback<Feature>() {
    override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
        return oldItem.iconResource == newItem.iconResource
    }

    override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean {
        return oldItem == newItem
    }
}

class FeaturesAdapter: ListAdapter<Feature, RecyclerView.ViewHolder>(FeatureDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemFeatureBinding>(inflater, R.layout.item_feature, parent, false)
        return FeatureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FeatureViewHolder).bind(getItem(position))
    }

    inner class FeatureViewHolder(
        private val binding: ItemFeatureBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Feature) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}