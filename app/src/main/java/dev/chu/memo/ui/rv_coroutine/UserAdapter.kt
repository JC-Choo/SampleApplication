package dev.chu.memo.ui.rv_coroutine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.databinding.ItemUserBinding
import dev.chu.memo.entity.User

class UserAdapter : ListAdapter<User, UserAdapter.ViewHolder>(Companion) {

    /**
     * === 와 == 차이
     * kotlin의 === 는 java의 ==와 같다. 즉, 주소를 비교한다.
     *
     * areItemsTheSame은 "두 객체들이 같은 아이템인지 아닌지를 확인할 때 사용하는 메서드"로 객체들이 각은지에 대해선 주소값을 비교해야 한다.
     * areContentsTheSame은 "두 아이템들이 같은 데이터인지 아닌지를 확인할 때 사용하는 메서드"로 아이템들이 같은 데이터인지만 확인하니, 아무 데이터나 같은지 확인해주면 된다.
     *
     * areItemsTheSame
     * This function decides whether to inflate the current view or to create a new view.
     * As shown above, we need to compare the primary-key value here like an id, which is unique for every item on the list.
     * 두 객체가 같은 항목인지 여부를 결정
     *
     * areContentsTheSame
     * List adapter only refreshes the view if this function returns true;
     * that’s because we may have many variables in the items which might not affect the UI, so changes in those variables shouldn’t affect the UI.
     * In order to work this effectively, we need to compare the item-variables that we use in the UI.
     * 두 항목의 데이터가 같은지 여부를 결정하며, areItemsTheSame()이 true를 반환하는 경우에만 호출
     *
     * boolean areItemsTheSame(oldItem, newItem) 를 통해 이젠 아이템과 새로운 아이템의 ID가 같은지 비교한 뒤 같은 경우
     * 내부에서 boolean areContentsTheSame(oldItem, newItem) 을 다시 호출하여서 객체의 필드가 같은지 비교할 수 있습니다.
     * 만약 areItemsTheSame 이 true를 반환하면 areContentsTheSame 는 호출되지 않습니다.
     */

    companion object: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id
//            oldItem === newItem

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
//            oldItem.id == newItem.id
    }

    interface ACallback {
        fun onClickEvent(item: User)
    }

    private var callback: ACallback? = null
    fun setCallback(callback: ACallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(layoutInflater)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = getItem(position)
        holder.binding.callback = callback
        holder.binding.user = currentUser
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}