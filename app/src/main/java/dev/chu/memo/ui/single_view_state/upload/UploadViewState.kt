package dev.chu.memo.ui.single_view_state.upload

sealed class UploadViewState

object Initial : UploadViewState()

data class UploadInProgress(val percentage: Int) : UploadViewState()

object UploadFailed : UploadViewState()

object UploadSuccess : UploadViewState()