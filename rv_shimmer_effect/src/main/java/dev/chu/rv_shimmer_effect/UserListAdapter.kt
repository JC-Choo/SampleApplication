package dev.chu.rv_shimmer_effect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_shimmer_effect.data.User
import dev.chu.rv_shimmer_effect.databinding.ItemUserListBinding

class UserListAdapter(
    private var userList: List<User>
) : RecyclerView.Adapter<UserListAdapter.TransactionViewHolder>() {

    fun swapData(newList: List<User>) {
        this.userList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemUserListBinding = DataBindingUtil.inflate(inflater, R.layout.item_user_list, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.binding.item = userList[position]
        holder.binding.executePendingBindings()
    }

    inner class TransactionViewHolder(
        val binding: ItemUserListBinding
    ) : RecyclerView.ViewHolder(binding.root)
}