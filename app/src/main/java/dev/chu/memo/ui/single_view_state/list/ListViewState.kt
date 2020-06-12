package dev.chu.memo.ui.single_view_state.list

data class ListViewState(
    val isLoading: Boolean,
    val items: List<String>
)