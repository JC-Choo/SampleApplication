package dev.chu.drag_and_drop.adapter

import android.content.ClipData
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.drag_and_drop.databinding.ItemWordBinding
import dev.chu.drag_and_drop.listener.DragListener

class WordsAdapter(
    private val onDragStarted: (String) -> Unit
) : ListAdapter<String, WordsAdapter.WordsViewHolder>(WordsDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun removeItem(selectedItem: String) {
        val list = ArrayList(currentList)
        list.remove(selectedItem)
        submitList(list)
    }

    inner class WordsViewHolder(
        private val binding: ItemWordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: String) {
            with(binding) {
                tvWord.text = word

                this.root.setOnLongClickListener { view ->
                    // when user is long clicking on a view, drag process will start.
                    val data = ClipData.newPlainText("", word)
                    val shadowBuilder = View.DragShadowBuilder(view)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        view.startDragAndDrop(data, shadowBuilder, view, 0)
                    } else {
                        view.startDrag(data, shadowBuilder, view, 0)
                    }
                    onDragStarted(word)

                    true
                }

                this.root.setOnDragListener(DragListener())
            }
        }
    }
}