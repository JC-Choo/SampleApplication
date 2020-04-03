package dev.chu.memo.ui.merge_adapter

sealed class LoadState {
    // Loading is in progress.
    object Loading: LoadState()

    // Loading is complete.
    object Done: LoadState()

    // Loading hit an error
    data class Error(val error: Throwable): LoadState() {
        override fun toString(): String = "Error: $error"
    }
}