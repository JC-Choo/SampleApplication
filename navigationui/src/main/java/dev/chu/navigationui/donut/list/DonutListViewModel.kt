package dev.chu.navigationui.donut.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chu.navigationui.model.Donut
import dev.chu.navigationui.storage.DonutDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DonutListViewModel(
    private val donutDao: DonutDao
): ViewModel() {

    // Users of this ViewModel will observe changes to its donuts list to know when
    // to redisplay those changes
    val donuts: LiveData<List<Donut>> = donutDao.getAll()

    fun delete(donut: Donut) = viewModelScope.launch(Dispatchers.IO) {
        donutDao.delete(donut)
    }
}