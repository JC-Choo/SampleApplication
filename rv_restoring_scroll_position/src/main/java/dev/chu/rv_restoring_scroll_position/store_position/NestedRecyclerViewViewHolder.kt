package dev.chu.rv_restoring_scroll_position.store_position

import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 를 포함하는 뷰홀더는 이 인터페이스를 상속해야 한다.
 * 뷰에 RecyclerView 가 포함 된 경우 대체 솔루션을 수동으로 검색 할 수 있습니다.
 */
interface NestedRecyclerViewViewHolder {
    fun getId(): String
    fun getLayoutManager(): RecyclerView.LayoutManager?
}