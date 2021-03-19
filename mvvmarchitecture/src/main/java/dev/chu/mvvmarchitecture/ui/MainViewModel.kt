package dev.chu.mvvmarchitecture.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.chu.mvvmarchitecture.data.MainRepository
import dev.chu.mvvmarchitecture.data.local.Model

/**
 * Activity 를 위해 DATA를 준비하고 관리하는데 책임이 있는 클래스.
 * repository에서 생성된 insert() 와 getAllCats() 함수를 호출한다.
 * ViewModel : View 와 Model 사이에 연결을 만든다.
 */
class MainViewModel(
    private val repository: MainRepository
): ViewModel() {

    private val getAllCats: LiveData<List<Model>> by lazy {
        repository.getAllCats()
    }

    fun insert(cats: List<Model>) {
        repository.insert(cats)
    }

    fun getAllCats(): LiveData<List<Model>> {
        return getAllCats
    }

    fun fetchData(callback: (List<Model>?) -> Unit) {
        repository.fetchData(callback)
    }
}