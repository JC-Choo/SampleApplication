package dev.chu.memo.data.repository

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.chu.memo.data.local.GithubDao
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.data.request.CreateIssueRequest
import dev.chu.memo.entity.GithubRepo
import dev.chu.memo.entity.Issue
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GithubRepository(private val api: ApiService, private val dao: GithubDao) {
    fun save(repo: GithubRepo) =
        dao.insert(repo)
            .subscribeOn(Schedulers.io())

    fun searchGithubRepos(q: String) =
        api.searchRepos(q)
            .map {
                it.asJsonObject?.getAsJsonArray("items")
            }
            .map {
                val type = object : TypeToken<List<GithubRepo>>() {}.type
                GsonBuilder().setLenient().create().fromJson(it, type) as List<GithubRepo>
            }
            .subscribeOn(Schedulers.io())

    fun checkStar(owner: String, repo: String): Completable =
        api.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun star(owner: String, repo: String): Completable =
        api.star(owner, repo)
            .subscribeOn(Schedulers.io())

    fun unstar(owner: String, repo: String): Completable =
        api.unstar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun issues(owner: String, repo: String): Single<List<Issue>> =
        api.issues(owner, repo)
            .subscribeOn(Schedulers.io())

    fun createIssue(owner: String, repo: String, title: String, body: String): Single<Issue> =
        api.createIssue(owner, repo, CreateIssueRequest(title, body))
            .subscribeOn(Schedulers.io())
}