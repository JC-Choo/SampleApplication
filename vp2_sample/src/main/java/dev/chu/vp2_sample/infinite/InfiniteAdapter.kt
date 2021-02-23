package dev.chu.vp2_sample.infinite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.vp2_sample.databinding.ItemFullLengthBinding

class InfiniteAdapter(
    itemListOriginal: List<String>
) : RecyclerView.Adapter<InfiniteViewHolder>() {

    private val itemList: List<String> = listOf(itemListOriginal.last() + "-Faked") +
            itemListOriginal +
            listOf(itemListOriginal.first() + "-Faked")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfiniteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFullLengthBinding.inflate(inflater, parent, false)
        return InfiniteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfiniteViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.count()
}