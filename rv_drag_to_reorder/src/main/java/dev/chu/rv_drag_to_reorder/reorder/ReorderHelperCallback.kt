package dev.chu.rv_drag_to_reorder.reorder

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_drag_to_reorder.utils.ItemTouchHelperAdapter

class ReorderHelperCallback(
    private val adapter: ItemTouchHelperAdapter
) : ItemTouchHelper.Callback() {
    /**
     * RecyclerView items 에 동작 플래그를 등록할 수 있는 함수
     * [ItemTouchHelper.Callback.makeMovementFlags] (이동 플래그를 만드는 편리한 방법)를
     * 사용함으로써 [swipe] 와 [drag] 플래그를 등록한다.
     * [swipe] 기능을 포함하길 원하지 않다면, 두번째 파라미너에 0을 주면 된다.
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, 0)
    }

    /**
     * [ItemTouchHelper]가 이전 포지션에서 새 포지션으로 아이템이 드래그되어 움직이길 원할 때 실행된다.
     * @param recyclerView : [ItemTouchHelper]가 붙은 RecyclerView
     * @param source : 유저에 의해 드래그되는 [RecyclerView.ViewHolder]
     * @param target : 현재 활성 항목을 드래그하고 있는 [RecyclerView.ViewHolder]
     */
    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // 활성화된 recyclerView adapter 와 함께 커뮤니케이트 되는
        // onMove 함수 내에서 해당 adapter interface 를 사용한다.
        adapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    /**
     * RecyclerView item swipe 를 검출하는데 사용되고, 현재는 필요가 없다.
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }
}