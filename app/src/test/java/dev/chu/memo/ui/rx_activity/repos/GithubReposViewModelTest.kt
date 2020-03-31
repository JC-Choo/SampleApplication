package dev.chu.memo.ui.rx_activity.repos

import dev.chu.memo.data.repository.GithubRepository
import dev.chu.memo.entity.GithubRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GithubReposViewModelTest {
    private lateinit var viewModel: GithubReposViewModel

    @Mock
    lateinit var githubRepo: GithubRepository

    private val repos: List<GithubRepo> = emptyList()
    private val searchText = "searchText"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = GithubReposViewModel(githubRepo)

        Mockito.`when`(githubRepo.searchGithubRepos(Mockito.anyString()))
            .thenReturn(Single.just(repos))
        Mockito.`when`(githubRepo.checkStar(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Completable.complete())
    }

    @Test
    fun searchGithubRepos() {
        viewModel.searchGithubRepos(searchText)
//        viewModel.reposState.test().assertValue(repos)
    }
}