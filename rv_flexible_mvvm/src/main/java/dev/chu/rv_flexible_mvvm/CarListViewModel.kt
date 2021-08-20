package dev.chu.rv_flexible_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CarListViewModel(
    private val carDataProvider: CarDataProvider = CarDataProvider()
): ViewModel() {
    /**
     * data
     * - UI를 위해 필요한 데이터를 갖고 있음
     * - ItemViewModel 인스턴스의 리스트를 갖고 있음
     */
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

    /**
     * [createViewData] 함수를 통해 리스트의 다른 객체(Header / CarAd / CarListing ViewModel)을 추가한다.
     */
    private fun createViewData(carsByMake: Map<String, List<CarData>>): List<ItemViewModel> {
        val viewData = mutableListOf<ItemViewModel>()
        carsByMake.keys.forEach {
            viewData.add(HeaderViewModel(it))
            val cars = carsByMake[it]
            cars?.forEach { car: CarData ->
                val item = if (car.isAd) {
                    CarAdViewModel(car.make, car.model, car.price, R.drawable.background_ad_red)
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