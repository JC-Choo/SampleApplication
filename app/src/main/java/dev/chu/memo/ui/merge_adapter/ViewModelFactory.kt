package dev.chu.memo.ui.merge_adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.chu.memo.data.repository.MergeRepository

class ViewModelFactory(private val repository: MergeRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchRepositoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchRepositoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}