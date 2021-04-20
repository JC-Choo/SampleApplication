package dev.chu.navigationui.coffee.entry

import androidx.lifecycle.*
import dev.chu.navigationui.model.Coffee
import dev.chu.navigationui.storage.CoffeeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoffeeEntryViewModel(
    private val coffeeDao: CoffeeDao
) : ViewModel() {

    private var coffeeLiveData: LiveData<Coffee>? = null

    fun get(id: Long): LiveData<Coffee> {
        return coffeeLiveData ?: liveData {
            emit(coffeeDao.get(id))
        }.also {
            coffeeLiveData = it
        }
    }

    fun addData(
        id: Long,
        name: String,
        description: String,
        rating: Int,
        setupNotification: (Long) -> Unit
    ) {
        val coffee = Coffee(id, name, description, rating)

        CoroutineScope(Dispatchers.IO).launch {
            var actualId = id

            if (id > 0) {
                update(coffee)
            } else {
                actualId = insert(coffee)
            }

            setupNotification(actualId)
        }
    }

    private suspend fun insert(donut: Coffee) = coffeeDao.insert(donut)

    private fun update(donut: Coffee) = viewModelScope.launch(Dispatchers.IO) {
        coffeeDao.update(donut)
    }

    val item = MutableLiveData<Coffee>()
}