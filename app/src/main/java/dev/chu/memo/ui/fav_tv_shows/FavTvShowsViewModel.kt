package dev.chu.memo.ui.fav_tv_shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.chu.memo.base.BaseViewModel
import dev.chu.memo.data.local.FavTvShows
import dev.chu.memo.data.local.FavTvShowsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavTvShowsViewModel(private val dao: FavTvShowsDao) : BaseViewModel() {

    init {
        addDataToRoom()
    }

    private fun addDataToRoom() {
        val list: ArrayList<FavTvShows> = ArrayList()
        val favTvShows1: FavTvShows = FavTvShows(name = "GOT", rating = 5, finished = false)
        val favTvShows2: FavTvShows = FavTvShows(name = "SUITS", rating = 3, finished = true)
        val favTvShows3: FavTvShows = FavTvShows(name = "WATCHMEN", rating = 4, finished = false)

        list.add(favTvShows1)
        list.add(favTvShows2)
        list.add(favTvShows3)

        viewModelScope.launch(Dispatchers.IO) {
            dao.setAllShows(list)
        }
    }

    fun addDataToRoomNew() {
        val list: ArrayList<FavTvShows> = ArrayList()
        val favTvShows: FavTvShows = FavTvShows(name = "LUCIFER", rating = 1, finished = true)
        list.add(favTvShows)
        viewModelScope.launch(Dispatchers.IO) {
            dao.setAllShows(list)
        }
    }

    fun getDataFromRoom() = dao.getAllShows()

    private val a: MutableLiveData<List<FavTvShows>> = MutableLiveData()
    val listFavTvShows: LiveData<List<FavTvShows>> get() = a
    suspend fun observeMovies() {
        dao.getAllShows().collect {
            if(it.isNotEmpty()) {
                a.value = it
            } else
                a.value = emptyList()
        }
    }
}