package dev.chu.rv_drag_to_reorder.utils

import androidx.recyclerview.widget.ItemTouchHelper

interface ItemTouchHelperViewHolder {
    /**
     * [ItemTouchHelper]는 처음 아이템을 움직이거나 스와이프 됨으로써 등록할 때 호출된다.
     * 활성화 상태를 가리키는 하이템 뷰를 업데이트 하기 위해 구현한다.
     */
    fun onItemSelected()

    /**
     * [ItemTouchHelper]는 움직이거나 스와외프가 완료하고 활성 아이템을 완료하면 호출된다.
     * 상태를 지운다.
     */
    fun onItemClear()
}