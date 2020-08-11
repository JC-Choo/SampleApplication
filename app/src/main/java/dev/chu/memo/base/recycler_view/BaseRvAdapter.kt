package dev.chu.basemodule.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.base.recycler_view.BaseDiffUtilItemCallback

class BaseRvAdapter<T : BaseItemModel>(
    private val resourceId: Int,
    private var variableId: Int,
    private var listItemOnClickListener: ListItemOnClickListener,
    baseDiffUtilItemCallback: BaseDiffUtilItemCallback<T>
) : RecyclerView.Adapter<BaseRvViewHolder<T>>() {

    private var asyncListDiffer: AsyncListDiffer<T> =
        AsyncListDiffer(this, baseDiffUtilItemCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder<T> {
        return BaseRvViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                resourceId,
                parent,
                false
            ), variableId, listItemOnClickListener
        )
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: BaseRvViewHolder<T>, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    fun getData(): List<T> = asyncListDiffer.currentList

    fun submitList(newItems: List<T>) {
        asyncListDiffer.submitList(newItems)
    }
}