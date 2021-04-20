package dev.chu.navigationui.donut.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chu.navigationui.model.Donut
import dev.chu.navigationui.storage.DonutDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 해당 뷰모델은 기본 데이터에 접근하고 데이터에 대한 변경 사항을 관찰하는데 사용된다.
 */
class DonutListViewModel(
    private val donutDao: DonutDao
) : ViewModel() {

    // 뷰모델 사용자는 도넛의 변화를 다시 보여줄 때 알고 있는 도넛 리스트의 변화를 관찰할 것이다.
    val donuts: LiveData<List<Donut>> = donutDao.getAll()

    fun delete(donut: Donut) = viewModelScope.launch(Dispatchers.IO) {
        donutDao.delete(donut)
    }
}