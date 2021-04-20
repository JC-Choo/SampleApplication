package dev.chu.navigationui.coffee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.chu.navigationui.coffee.entry.CoffeeEntryViewModel
import dev.chu.navigationui.coffee.list.CoffeeListViewModel
import dev.chu.navigationui.storage.CoffeeDao

@Suppress("UNCHECKED_CAST")
class CoffeeViewModelFactory(
    private val coffeeDao: CoffeeDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeListViewModel::class.java)) {
            return CoffeeListViewModel(coffeeDao) as T
        } else if (modelClass.isAssignableFrom(CoffeeEntryViewModel::class.java)) {
            return CoffeeEntryViewModel(coffeeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}