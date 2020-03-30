package dev.chu.memo.ui.mvi.etc

//LCE -> Loading/Content/Error
sealed class LCE<out T> {
    data class Success<T>(val data: T) : LCE<T>()
    data class Error<T>(val message: String) : LCE<T>() {
        constructor(t: Throwable) : this(t.message ?: "")
    }
}