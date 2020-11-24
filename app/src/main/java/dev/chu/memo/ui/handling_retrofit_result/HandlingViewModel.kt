package dev.chu.memo.ui.handling_retrofit_result

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chu.memo.common.Const
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.entity.Repository
import dev.chu.module_network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HandlingViewModel : ViewModel() {
    // Retrofit service
    private val service: ApiService = Api.createService(ApiService::class.java, Const.URL_GITHUB)

    // for internal usage
    private val _listUser = MutableLiveData<ResultOf<Repository>>()

    // Expose to the outside world
    val listUser: LiveData<ResultOf<Repository>>
        get() = _listUser

    @UiThread
    fun fetchUsersFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.getPopularRepos()
                _listUser.postValue(ResultOf.Success(response))
            } catch (ioe: IOException) {
                _listUser.postValue(ResultOf.Failure("[IO] error please retry", ioe))
            } catch (he: HttpException) {
                _listUser.postValue(ResultOf.Failure("[HTTP] error please retry", he))
            }
        }
    }
}