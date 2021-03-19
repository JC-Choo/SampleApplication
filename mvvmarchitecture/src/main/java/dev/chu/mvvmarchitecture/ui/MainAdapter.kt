package dev.chu.mvvmarchitecture.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.mvvmarchitecture.data.local.Model
import dev.chu.mvvmarchitecture.databinding.ItemPicBinding
import dev.chu.mvvmarchitecture.etc.imageUrl

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val items = mutableListOf<Model>()
    fun setNewItems(newItems: List<Model>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPicBinding.inflate(layoutInflater)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    class MainViewHolder(
        private val binding: ItemPicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Model) {
            binding.itempic.imageUrl(item.url ?: "")
        }
    }
}