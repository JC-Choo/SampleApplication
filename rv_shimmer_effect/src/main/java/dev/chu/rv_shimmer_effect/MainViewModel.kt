package dev.chu.rv_shimmer_effect

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chu.rv_shimmer_effect.data.Resource
import dev.chu.rv_shimmer_effect.data.User
import dev.chu.rv_shimmer_effect.data.UserProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val userListObserver = MutableLiveData<Resource<List<User>>>()

    fun getUserList() {
        userListObserver.value = Resource.Loading()
        viewModelScope.launch {
            delay(3000)
            userListObserver.value = Resource.Success(UserProvider.getUserList())
        }
    }
}