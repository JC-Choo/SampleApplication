package dev.chu.memo.etc

interface DataListener<T> {
    fun onSuccess(t: T)
}