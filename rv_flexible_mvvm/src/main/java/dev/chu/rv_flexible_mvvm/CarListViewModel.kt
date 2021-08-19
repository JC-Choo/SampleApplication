package dev.chu.rv_flexible_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CarListViewModel(
    private val carDataProvider: CarDataProvider = CarDataProvider()
): ViewModel() {
    val data: LiveData<List<ItemViewModel>>
        get() = _data
    private val _data = MutableLiveData<List<ItemViewModel>>(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val carList = carDataProvider.getCarListData()

            val carsByMake = carList.groupBy { it.make }

            val viewData = createViewData(carsByMake)
            _data.postValue(viewData)
        }
    }

    private fun createViewData(carsByMake: Map<String, List<CarData>>): List<ItemViewModel> {
        val viewData = mutableListOf<ItemViewModel>()
        carsByMake.keys.forEach {
            viewData.add(HeaderViewModel(it))
            val cars = carsByMake[it]
            cars?.forEach { car: CarData ->
                val item = if (car.isAd) {
                    CarAdViewModel(car.make, car.model, car.price)
                } else {
                    CarListingViewModel(car.make, car.model, car.price)
                }
                viewData.add(item)
            }
        }

        return viewData
    }

    companion object {
        const val HEADER_ITEM = 0
        const val LISTING_ITEM = 1
        const val AD_ITEM = 2
    }
}