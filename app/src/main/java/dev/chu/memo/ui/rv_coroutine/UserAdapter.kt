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
     */

    companion object: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id
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