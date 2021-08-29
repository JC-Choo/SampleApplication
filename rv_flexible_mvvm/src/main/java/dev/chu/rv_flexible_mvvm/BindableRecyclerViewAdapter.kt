package dev.chu.rv_flexible_mvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindableRecyclerViewAdapter : RecyclerView.Adapter<BindableViewHolder>() {

    /**
     * itemViewModels : list의 내용을 나타냄
     * viewTypeToLayoutId
     * - 특정 view type 의 layout id 를 가진 어댑터의 가능한 뷰타입에 관련있는 맵 형태
     * - viewType 을 알아야만 ViewHolder 를 만드는데, ViewHolder 를 인플레이트하기 위해선 layout 이 무엇인지 알아야만 하기에 중요!
     */
    private var itemViewModels: List<ItemViewModel> = emptyList()
    private val viewTypeToLayoutId: MutableMap<Int, Int> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewTypeToLayoutId[viewType] ?: 0,
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = itemViewModels[position]
        if (!viewTypeToLayoutId.containsKey(item.viewType)) {
            viewTypeToLayoutId[item.viewType] = item.layoutId
        }
        return item.viewType
    }

    override fun getItemCount(): Int = itemViewModels.size

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(itemViewModels[position])
    }

    fun updateItems(items: List<ItemViewModel>?) {
        itemViewModels = items ?: emptyList()
        notifyItemRangeChanged(0, itemViewModels.size, itemViewModels)
    }
}

class BindableViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(itemViewModel: ItemViewModel) {
        binding.setVariable(BR.itemViewModel, itemViewModel)
    }
}