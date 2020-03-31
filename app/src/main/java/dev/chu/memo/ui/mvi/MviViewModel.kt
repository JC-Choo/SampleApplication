package dev.chu.memo.ui.mvi

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.viewModelScope
import dev.chu.memo.data.repository.UsersRepository
import dev.chu.memo.entity.User
import dev.chu.memo.ui.mvi.aac.AacMviViewModel
import dev.chu.memo.ui.mvi.etc.LCE
import kotlinx.coroutines.launch

class MviViewModel(
    application: Application,
    private val repository: UsersRepository
) : AacMviViewModel<MviViewState, MviViewEffect, MviViewEvent>(application) {

    private var count: Int = 0

    init {
        viewState = MviViewState(fetchStatus = FetchStatus.NotFetched, userList = emptyList())
    }

    override fun process(viewEvent: MviViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is MviViewEvent.UsersItemClicked -> usersItemClicked(viewEvent.item)
            is MviViewEvent.FabClicked -> fabClicked()
            is MviViewEvent.OnSwipeRefresh -> fetchUsers()
            is MviViewEvent.FetchUsers -> fetchUsers()
        }
    }

    private fun usersItemClicked(item: User) {
        viewEffect = MviViewEffect.ShowSnackbar(item.userName)
    }

    private fun fabClicked() {
        count++
        viewEffect = MviViewEffect.ShowToast(message = "Fab clicked count $count")
    }

    @SuppressLint("CheckResult")
    private fun fetchUsers() {
        viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
        viewModelScope.launch {
            when (val result = repository.getUsersFromServer()) {
                is LCE.Success -> {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, userList = result.data)
                }
                is LCE.Error -> {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                    viewEffect = MviViewEffect.ShowToast(message = result.message)
                }
            }
        }

//        compositeDisposable += repository.getUsersFromServerByRx { result ->
//            when(result) {
//                is LCE.Success -> {
//                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, userList = result.data)
//                }
//                is LCE.Error -> {
//                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
//                    viewEffect = MviViewEffect.ShowToast(message = result.message)
//                }
//            }
//        }
    }
}