package dev.chu.basemodule.recycler_view

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

interface ListItemOnClickListener {
    fun onItemClick(adapterPosition: Int, view: View?)
    fun onItemLongClick(adapterPosition: Int, view: View?): Boolean
}

class BaseRvViewHolder<T>(
    viewDataBinding: ViewDataBinding,
    variableId: Int,
    listItemOnClickListener: ListItemOnClickListener
) : RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener, View.OnLongClickListener {

    private var viewDataBinding: ViewDataBinding? = null
    private var variableId: Int = 0
    private var listItemOnClickListener: ListItemOnClickListener? = null

    init {
        this.viewDataBinding = viewDataBinding
        this.variableId = variableId
        this.listItemOnClickListener = listItemOnClickListener

        this.listItemOnClickListener?.let {
            viewDataBinding.root.also {
                it.setOnClickListener(this)
                it.setOnLongClickListener(this)
            }
        }
    }

    fun bind(type: T) {
        viewDataBinding?.setVariable(variableId, type)
        viewDataBinding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        listItemOnClickListener?.onItemClick(adapterPosition, v)
    }

    override fun onLongClick(v: View?): Boolean {
        return listItemOnClickListener!!.onItemLongClick(adapterPosition, v)
    }
}