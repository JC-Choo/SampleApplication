package dev.chu.rv_checkbox_selection.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_checkbox_selection.databinding.ItemTestBinding

data class Test(
    val title: String,
    val name: String
)

class TestAdapter : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    private val items = ArrayList<Test>()

    fun setItems(newItems: List<Test>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val binding = ItemTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    class TestViewHolder(
        private val binding: ItemTestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Test) {
            binding.videoTitle.text = item.title
            binding.channelName.text = item.name

            binding.executePendingBindings()
        }
    }
}