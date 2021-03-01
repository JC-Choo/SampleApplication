package dev.chu.rv_restoring_scroll_position

import androidx.recyclerview.widget.RecyclerView

// ViewHolders containing a RecyclerView should inherit this interface.
// An alternative solution could be manually searching if the view contains a RecyclerView
interface NestedRecyclerViewViewHolder {
    fun getId(): String
    fun getLayoutManager(): RecyclerView.LayoutManager?
}