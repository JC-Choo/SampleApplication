package dev.chu.rv_drag_to_reorder.utils

interface ItemTouchHelperAdapter {
    /**
     * 아이템이 충분히 드레그됐을 때 호출.
     * 아이템이 움직였을 때마다 호출되고, "드롭" 이벤트의 마지막에 호출되지 않는다?
     *
     * @param fromPosition : 이동된 아이템의 시작 위치
     * @param toPosition : 이동된 아이템의 위치 결정된 위치
     * @return 새 어댑터 위치에 이동됐다면, True
     */
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    /**
     * 스와이프에 의해 아이템이 사라졌을 때 호출
     *
     * @param position : 사라진 아이템의 위치
     */
    fun onItemDismiss(position: Int)
}