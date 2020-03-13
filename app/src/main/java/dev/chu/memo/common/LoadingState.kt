package dev.chu.memo.common

//sealed class Result<out T : Any> {
//    data class Success<out T : Any>(val data: T): Result<T>()
//    data class Error(val e: Exception): Result<Nothing>()
//}

data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}