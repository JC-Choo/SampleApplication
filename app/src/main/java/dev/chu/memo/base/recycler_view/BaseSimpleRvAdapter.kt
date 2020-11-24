package dev.chu.basemodule.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class BaseSimpleRvAdapter<T>(
    private val resourceId: Int,
    private val variableId: Int,
    private val listItemOnClickListener: ListItemOnClickListener
) : RecyclerView.Adapter<BaseRvViewHolder<T>>() {

    private var objList: List<T> = emptyList()

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

    override fun getItemCount(): Int = if(objList.isNotEmpty()) objList.size else 0

    override fun onBindViewHolder(holder: BaseRvViewHolder<T>, position: Int) {
        if(objList.isNotEmpty()) {
            holder.bind(objList[position])
        }
    }

    fun getData(): List<T> = objList
    fun submitList(newItems: List<T>) {
        this.objList = newItems
        notifyDataSetChanged()
    }
}