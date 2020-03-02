package dev.chu.memo.data

import dev.chu.memo.etc.extension.exhaustive

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object InProgress: Result<Nothing>()
}

fun handleResult(result: Result<Int>) {
    when(result) {
        is Result.Success -> {

        }
        is Result.Error -> {

        }
        Result.InProgress -> TODO()
    }.exhaustive
}