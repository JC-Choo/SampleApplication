package dev.chu.memo.ui.fav_tv_shows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.data.local.FavTvShows
import dev.chu.memo.databinding.ItemFavTvShowBinding

class FavTvShowsAdapter(private val items: MutableList<FavTvShows>) : RecyclerView.Adapter<FavTvShowsAdapter.ViewHolder>() {
    fun addItem(item: FavTvShows) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun setItems(newItems: List<FavTvShows>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav_tv_show, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = items[position]
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemFavTvShowBinding>(view)!!
    }
}