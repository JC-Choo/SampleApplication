package dev.chu.memo.ui.rx_activity.repos

import dev.chu.memo.base.RxBaseViewModel
import dev.chu.memo.data.repository.GithubRepository
import dev.chu.memo.entity.GithubRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class GithubReposViewModel (private val repository: GithubRepository): RxBaseViewModel() {
    private val searchSubject = BehaviorSubject.createDefault("" to false)
    val loadingState = PublishSubject.create<Boolean>()
    val reposState = PublishSubject.create<List<GithubRepo>>()

    fun onCreate() {
        compositeDisposable += searchSubject
            .debounce(400, TimeUnit.MILLISECONDS)   // 사람이 타이핑 할 때마다 검색하면 안되니까 해주는 것! 타이머랑 비슷하다 생각하면 될듯!
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())

            .doOnNext { loadingState.onNext(it.second) }
            .observeOn(Schedulers.io())
            .switchMapSingle {
                if(it.first.isEmpty()) Single.just(emptyList())
                else repository.searchGithubRepos(it.first)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { loadingState.onNext(false) }

            .subscribe {
                reposState.onNext(it)
            }
    }

    fun searchGithubRepos(search: String) {
        searchSubject.onNext(search to true)
    }

    fun searchGithubRepos() {
        searchSubject.onNext(searchSubject.value!!.first to false)
    }
}