package dev.chu.mvvmarchitecture.data.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalDataSource(
    private val database: CatDatabase
) {
    private val catImageDao: CatImageDao by lazy { database.catImageDao() }
    private val getAllCats: LiveData<List<Model>> by lazy { catImageDao.getCats() }

    fun insert(cats: List<Model>) {
        CoroutineScope(Dispatchers.IO).launch {
            catImageDao.insert(cats)
        }
    }

    fun getAllCats(): LiveData<List<Model>> {
        return getAllCats
    }
}