package dev.chu.memo.ui.mvi

import android.app.Application
import androidx.lifecycle.viewModelScope
import dev.chu.memo.data.repository.UsersRepository
import dev.chu.memo.entity.User
import dev.chu.memo.ui.mvi.aac.AacMviViewModel
import dev.chu.memo.ui.mvi.etc.LCE
import kotlinx.coroutines.launch

class MviActViewModel(application: Application, private val repository: UsersRepository) :
    AacMviViewModel<MainViewState, MainViewEffect, MainViewEvent>(application) {
    private var count: Int = 0

    init {
        viewState = MainViewState(fetchStatus = FetchStatus.NotFetched, userList = emptyList())
    }

    override fun process(viewEvent: MainViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is MainViewEvent.UsersItemClicked -> usersItemClicked(viewEvent.item)
            is MainViewEvent.FabClicked -> fabClicked()
            is MainViewEvent.OnSwipeRefresh -> fetchUsers()
            is MainViewEvent.FetchUsers -> fetchUsers()
        }
    }

    private fun usersItemClicked(item: User) {
        viewEffect = MainViewEffect.ShowSnackbar(item.userName)
    }

    private fun fabClicked() {
        count++
        viewEffect = MainViewEffect.ShowToast(message = "Fab clicked count $count")
        viewEffect = MainViewEffect.ShowSnackbar("count = $count")
    }

    private fun fetchUsers() {
        viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
        viewModelScope.launch {
            when (val result = repository.getUsersFromServer()) {
                is LCE.Error -> {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                    viewEffect = MainViewEffect.ShowToast(message = result.message)
                }
                is LCE.Success -> {
                    viewState =
                        viewState.copy(fetchStatus = FetchStatus.Fetched, userList = result.data)
                }
            }
        }
    }
}