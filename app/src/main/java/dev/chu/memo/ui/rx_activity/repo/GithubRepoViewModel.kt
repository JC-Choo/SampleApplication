package dev.chu.memo.ui.rx_activity.repo

import dev.chu.memo.base.RxBaseViewModel
import dev.chu.memo.data.repository.GithubRepository
import dev.chu.memo.entity.GithubRepo
import dev.chu.memo.entity.Issue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject

class GithubRepoViewModel(private val repository: GithubRepository) : RxBaseViewModel() {
    val repoState = BehaviorSubject.create<GithubRepo>()
    val issuesState = BehaviorSubject.createDefault<List<Issue>>(emptyList())

    fun onCreate(repo: GithubRepo) {
        repoState.onNext(repo)
        compositeDisposable += repository.issues(repo.owner.userName, repo.name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(issuesState::onNext)
    }

    fun save(repo: GithubRepo) =
        repository.save(repo).subscribe()

    fun onClickStar() {
        val repo = repoState.value!!
        val doStar = !repo.star
        repoState.onNext(
            repo.copy(
                star = !repo.star,
                stargazersCount =
                if (doStar) repo.stargazersCount + 1
                else repo.stargazersCount - 1
            )
        )
        compositeDisposable +=
            (if (doStar)
                repository.star(repo.owner.userName, repo.name)
            else
                repository.unstar(repo.owner.userName, repo.name))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, {
                    repoState.onNext(repo)
                })
    }
}