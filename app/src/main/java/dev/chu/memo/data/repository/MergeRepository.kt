package dev.chu.memo.data.repository

import android.util.Log
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.data.remote.RepoSearchResult
import dev.chu.memo.entity.Repo
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1
private const val IN_QUALIFIER = "in:name,description"

/**
 * Repository class that works with local and remote data sources.
 */
class MergeRepository(private val service: ApiService) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Repo>()

    // keep channel of results. The channel allows us to broadcast updates so
    // the subscriber will have the latest data
    private val searchResults = ConflatedBroadcastChannel<RepoSearchResult>()

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    suspend fun getSearchResultStream(query: String): Flow<RepoSearchResult> {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
        requestAndSaveData(query)

        return searchResults.asFlow()
    }

    suspend fun requestMore(query: String) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }

    suspend fun retry(query: String) {
        if (isRequestInProgress) return
        requestAndSaveData(query)
    }

    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false

        val apiQuery = query + IN_QUALIFIER
        try {
            val response = service.searchRepos(apiQuery, lastRequestedPage, NETWORK_PAGE_SIZE)
            Log.d("GithubRepository", "response $response")
            if (response.isSuccessful) {
                if (response.isSuccessful) {
                    val repos = response.body()?.items ?: emptyList()
                    inMemoryCache.addAll(repos)
                    val reposByName = reposByName(query)
                    searchResults.offer(RepoSearchResult.Success(reposByName))
                    successful = true
                } else {
                    Log.d("GithubRepository", "fail to get data")
                    searchResults.offer(RepoSearchResult.Error(IOException(response.message()
                        ?: "Unknown error")))
                }
            } else {
                Log.d("GithubRepository", "fail to get data")
                searchResults.offer(RepoSearchResult.Error(IOException(response.message()
                    ?: "Unknown error")))
            }
        } catch (exception: IOException) {
            searchResults.offer(RepoSearchResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(RepoSearchResult.Error(exception))
        }

        isRequestInProgress = false
        return successful
    }

    private fun reposByName(query: String): List<Repo> {
        // from the in memory cache select only the repos whose name or description matches
        // the query. Then order the results.
        return inMemoryCache.filter {
            it.name.contains(query, true) ||
                    (it.description != null && it.description.contains(query, true))
        }.sortedWith(compareByDescending<Repo> { it.stars }.thenBy { it.name })
    }
}