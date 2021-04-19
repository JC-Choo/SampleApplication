package dev.chu.navigationui.donut

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.chu.navigationui.donut.entry.DonutEntryViewModel
import dev.chu.navigationui.donut.list.DonutListViewModel
import dev.chu.navigationui.storage.DonutDao

class DonutViewModelFactory(
    private val donutDao: DonutDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonutListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonutListViewModel(donutDao) as T
        } else if (modelClass.isAssignableFrom(DonutEntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonutEntryViewModel(donutDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}