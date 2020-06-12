package dev.chu.memo.ui.merge_adapter

import androidx.lifecycle.*
import dev.chu.memo.base.BaseViewModel
import dev.chu.memo.data.remote.RepoSearchResult
import dev.chu.memo.data.repository.GithubRepository
import dev.chu.memo.data.repository.MergeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for the [SearchRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */

class SearchRepositoriesViewModel(private val repository: MergeRepository): BaseViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val _repoLoadStatus = MutableLiveData<LoadState>()
    val repoLoadStatus: LiveData<LoadState>
        get() = _repoLoadStatus.distinctUntilChanged()

    private val queryLiveData = MutableLiveData<String>()
    val repoResult: LiveData<RepoSearchResult> = queryLiveData.switchMap { queryString ->
        liveData {
            val repos = repository.getSearchResultStream(queryString).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }.map {
            // update the load status based on the result type
            when (it) {
                is RepoSearchResult.Success -> _repoLoadStatus.value = LoadState.Done
                is RepoSearchResult.Error -> _repoLoadStatus.value = LoadState.Error(it.error)
            }
            it
        }
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                _repoLoadStatus.postValue(LoadState.Loading)
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }

    fun retry() {
        queryLiveData.value?.let { query ->
            _repoLoadStatus.value = LoadState.Loading
            viewModelScope.launch {
                repository.retry(query)
            }
        }
    }
}