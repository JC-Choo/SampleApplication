package dev.chu.rv_bounce_effect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_bounce_effect.databinding.ItemTextBinding

class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {

    private val items = mutableListOf<String>()

    fun setNewItems(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTextBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()
}

class MyViewHolder(
    private val binding: ItemTextBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(str: String) {
        binding.text.text = str
    }
}