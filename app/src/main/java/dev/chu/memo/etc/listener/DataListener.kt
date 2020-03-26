package dev.chu.memo.etc.listener

interface DataListener<T> {
    fun onSuccess(t: T)
    fun onFail(e: Throwable)
}