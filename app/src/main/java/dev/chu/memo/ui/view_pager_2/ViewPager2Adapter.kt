package dev.chu.memo.ui.view_pager_2

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.etc.extension.TAG

class ViewPager2Adapter(
    private val items: MutableList<String>
) : RecyclerView.Adapter<ViewPager2ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager_2, parent, false)
        return ViewPager2ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewPager2ViewHolder, position: Int) {
        Log.e(TAG, "position = $position")
        when(position) {
            0 -> {
                holder.bind(items.subList(0, 3))
            }
            1 -> {
                holder.bind(items.subList(3, 6))
            }
        }
    }
}

class RvAdapter(
    private val items: MutableList<String>
): RecyclerView.Adapter<RvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager_2_rv, parent, false)
        return RvViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(items[position])
    }
}