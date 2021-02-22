package dev.chu.rv_drag_to_reorder.utils

import androidx.recyclerview.widget.RecyclerView

/**
 * 드래그 시작을 뷰가 요청할 때 실행되는 하나의 함수를 가진 인터페이스
 */
interface OnStartDragListener {
    /**
     * 뷰가 드래그의 시작을 요청할 때 호출
     *
     * @param viewHolder : 드래그하는 뷰의 홀더
     */
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder?)
}