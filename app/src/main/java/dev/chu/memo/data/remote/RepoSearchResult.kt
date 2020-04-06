package dev.chu.memo.data.remote

import dev.chu.memo.entity.Repo

sealed class RepoSearchResult {
    data class Success(val data: List<Repo>): RepoSearchResult()
    data class Error(val error: Exception): RepoSearchResult()
}