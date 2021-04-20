package dev.chu.navigationui.coffee.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chu.navigationui.model.Coffee
import dev.chu.navigationui.storage.CoffeeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 해당 뷰모델은 기본 데이터에 접근하고 데이터에 대한 변경 사항을 관찰하는데 사용된다.
 */
class CoffeeListViewModel(
    private val coffeeDao: CoffeeDao
): ViewModel() {

    // 이 뷰모델 사용자는 커피의 변화를 다시 보여주려 할 때 알고 있는 커피 리스트의 변화를 관찰할 것이다.
    val coffeeList: LiveData<List<Coffee>> = coffeeDao.getAll()

    fun delete(coffee: Coffee) = viewModelScope.launch(Dispatchers.IO) {
        coffeeDao.delete(coffee)
    }
}