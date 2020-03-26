package dev.chu.memo.ui.memo_add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.data.local.ImageData
import dev.chu.memo.databinding.ItemImageDeleteBinding

class ImageModifyAdapter(private val items: MutableList<ImageData>) :
    RecyclerView.Adapter<ImageModifyAdapter.ViewHolder>() {

    interface ACallback {
        fun onClickDeleteImage(data: ImageData)
    }

    private var callback: ACallback? = null
    fun setCallback(callback: ACallback) {
        this.callback = callback
    }

    fun addItem(item: ImageData) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun setItems(item: List<ImageData>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_delete, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = items[position]
        holder.bind(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemImageDeleteBinding>(view)!!
        fun bind(item: ImageData) {
            binding.imageIvDelete.setOnClickListener {
                callback?.onClickDeleteImage(item)
            }
        }
    }
}