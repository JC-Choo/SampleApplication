package dev.chu.navigationui.donut.entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dev.chu.navigationui.model.Donut
import dev.chu.navigationui.storage.DonutDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DonutEntryViewModel(
    private val donutDao: DonutDao
) : ViewModel() {

    private var donutLiveData: LiveData<Donut>? = null

    fun get(id: Long): LiveData<Donut> {
        return donutLiveData ?: liveData {
            emit(donutDao.get(id))
        }.also {
            donutLiveData = it
        }
    }

    fun addData(
        id: Long,
        name: String,
        description: String,
        rating: Int,
        setupNotification: (Long) -> Unit
    ) {
        val donut = Donut(id, name, description, rating)

        CoroutineScope(Dispatchers.IO).launch {
            var actualId = id

            if (id > 0) {
                update(donut)
            } else {
                actualId = insert(donut)
            }

            setupNotification(actualId)
        }
    }

    private suspend fun insert(donut: Donut): Long {
        return donutDao.insert(donut)
    }

    private fun update(donut: Donut) = viewModelScope.launch(Dispatchers.IO) {
        donutDao.update(donut)
    }
}