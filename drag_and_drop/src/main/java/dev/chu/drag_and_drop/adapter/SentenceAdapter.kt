package dev.chu.drag_and_drop.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.drag_and_drop.databinding.ItemWordBinding

class SentenceAdapter : ListAdapter<String, SentenceAdapter.WordsViewHolder>(WordsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWordBinding.inflate(layoutInflater, parent, false)
        return WordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addItem(selectedWord: String) {
        val list = ArrayList(currentList)
        list.add(selectedWord)
        submitList(list)
    }

    inner class WordsViewHolder(
        private val binding: ItemWordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: String) {
            Log.i("Result", "sentence word = $word")
            with(binding) {
                tvWord.text = word
            }
        }
    }
}