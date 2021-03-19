package dev.chu.mvvmarchitecture.data

import androidx.lifecycle.LiveData
import dev.chu.mvvmarchitecture.data.local.LocalDataSource
import dev.chu.mvvmarchitecture.data.local.Model
import dev.chu.mvvmarchitecture.data.remote.RemoteDataSource

/**
 * database 에 data 를 삽입할 수 있는 클래스.
 * 백그라운드 작업으로 database 에 삽입해야만 한다.
 * insert() fun 에서 AsyncTask class 호출해야 하며,
 * database 안에 repository 를 통해 삽입된 삽입 데이터 모두는 getAllCats() 함수를 사용해 호출한다.
 */
class MainRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun insert(cats: List<Model>) {
        localDataSource.insert(cats)
    }

    fun getAllCats(): LiveData<List<Model>> {
        return localDataSource.getAllCats()
    }

    fun fetchData(callback: (List<Model>?) -> Unit) {
        remoteDataSource.fetchData(callback)
    }
}