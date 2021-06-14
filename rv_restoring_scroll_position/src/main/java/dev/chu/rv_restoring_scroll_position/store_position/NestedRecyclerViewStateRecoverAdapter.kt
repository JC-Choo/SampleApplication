package dev.chu.rv_restoring_scroll_position.store_position

import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

/**
 * @see layoutManagerStates : 레이아웃 매니저 상태를 저장할 변수
 * @see visibleScrollableViews : NestedRecyclerViewViewHolder 를 상속한 ViewHolder 를 담을 변수
 */
abstract class NestedRecyclerViewStateRecoverAdapter<T, VH : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtil) {

    private val layoutManagerStates = hashMapOf<String, Parcelable?>()
    private val visibleScrollableViews = hashMapOf<Int, ViewHolderRef>()

    @CallSuper
    override fun onViewRecycled(holder: VH) {
        if (holder is NestedRecyclerViewViewHolder) {
            // 스크롤 포지션 상태(레이아웃 매니저 상태)를 저장한다.
            val state = holder.getLayoutManager()?.onSaveInstanceState()
            layoutManagerStates[holder.getId()] = state

            // 상태를 저장하고 뷰는 보여주지 않는다.
            visibleScrollableViews.remove(holder.hashCode())
        }
        super.onViewRecycled(holder)
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder is NestedRecyclerViewViewHolder) {
            holder.getLayoutManager()?.let {
                // 저장된 레이아웃 매니저 상태 검색 및 설정
                val state: Parcelable? = layoutManagerStates[holder.getId()]
                if (state != null) {
                    it.onRestoreInstanceState(state)
                } else {
                    // 상태를 복원할 필요가 없을 때 스크롤 위치를 재설정해야 한다.
                    it.scrollToPosition(0)
                }
            }

            visibleScrollableViews[holder.hashCode()] = ViewHolderRef(
                holder.getId(),
                WeakReference(holder as NestedRecyclerViewViewHolder)
            )
        }
    }

    @CallSuper
    override fun submitList(list: List<T>?) {
        // 이들의 상태를 보존하기 위해 리스트를 업데이트/설정하기 전에 보이는 뷰에서 상태를 저장해야 한다.
        saveState()
        super.submitList(list)
    }

    private fun saveState() {
        visibleScrollableViews.values.forEach { item ->
            item.reference.get()?.let {
                layoutManagerStates[item.id] = it.getLayoutManager()?.onSaveInstanceState()
            }
        }
    }

    fun clearState() {
        layoutManagerStates.clear()
        visibleScrollableViews.clear()
    }
}

data class ViewHolderRef(
    val id: String,
    val reference: WeakReference<NestedRecyclerViewViewHolder>
)