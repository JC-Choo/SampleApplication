package dev.chu.sealed_class_manage_ui.ui_main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chu.sealed_class_manage_ui.data.MainScreenState
import dev.chu.sealed_class_manage_ui.data.ResourceManager
import dev.chu.sealed_class_manage_ui.data.repository.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 모든 비지느스 로직은 ViewModel 로!!
 * 화면 전환과 같은 구성 변화에도 살아있는 데이터를 관리하고 저장하는데 돕기 위한 생명주기를 인식하는 클래스.
 *
 * 큰 어플리케이션에는 로직과 책임을 분리하기 위한 presenter 또는 use case 와 같은 중간 계층이 필요하지만,
 * 이 예제에서는 repository 를 갖는 것으로 충분하다.
 *
 * 좋은 해결책은 repository 를 삽입하는 것!! 어떻게 ? 대거, 코인과 같은 DI framework 를 사용해서!
 */
//class MainViewModel() : ViewModel() {
//
//    private val repository: MainRepository = MainRepositoryImpl()
//    private val defaultError = "Something went wrong. Please try again later."
//    val screenState = MutableLiveData<MainScreenState>()
//
//    fun fetchData() {
//        if (screenState.value is MainScreenState.Success) return
//        screenState.value = MainScreenState.Loading
//        viewModelScope.launch {
//            delay(1000)
//            try {
//                screenState.value = MainScreenState.Success(repository.getData())
//            } catch (e: Exception) {
//                screenState.value = MainScreenState.Error(e.message ?: defaultError)
//            }
//        }
//    }
//}

class MainViewModel(
    private val repository: MainRepository,
    resource: ResourceManager
    ) : ViewModel() {

    private val defaultError = resource.errorMsg
    val screenState = MutableLiveData<MainScreenState>()

    fun fetchData() {
        if (screenState.value is MainScreenState.Success) return
        screenState.value = MainScreenState.Loading
        viewModelScope.launch {
            delay(1000)
            try {
                screenState.value = MainScreenState.Success(repository.getData())
            } catch (e: Exception) {
                screenState.value = MainScreenState.Error(e.message ?: defaultError)
            }
        }
    }
}