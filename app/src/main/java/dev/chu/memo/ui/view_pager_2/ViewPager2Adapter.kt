package dev.chu.memo.ui.view_pager_2

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.etc.extension.TAG

//class ViewPager2Adapter(
//    private val items: MutableList<String>
//) : RecyclerView.Adapter<ViewPager2ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager_2, parent, false)
//        return ViewPager2ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onBindViewHolder(holder: ViewPager2ViewHolder, position: Int) {
//        Log.e(TAG, "position = $position")
//        when(position) {
//            0 -> {
//                holder.bind(items.subList(0, 3))
//            }
//            1 -> {
//                holder.bind(items.subList(3, 6))
//            }
//        }
//    }
//}

class RvAdapter : RecyclerView.Adapter<RvViewHolder>() {

    private val items: MutableList<PagerItem> = mutableListOf()

    fun setNewItems(newItems: List<PagerItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
//        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager_2_rv, parent, false)
        view.layoutParams.width = parent.width / (ITEMS_PER_PAGE / 2)
        view.layoutParams.height = parent.height / (ITEMS_PER_PAGE / 2)

        Log.e(TAG, "view.width = ${view.layoutParams.width}")
        Log.e(TAG, "view.height = ${view.layoutParams.height}")
        Log.e(TAG, "parent.width = ${parent.width}")
        Log.e(TAG, "parent.height = ${parent.height}")
        Log.e(TAG, "parent.measuredWidth = ${parent.measuredWidth}")
        Log.e(TAG, "parent.measuredHeight = ${parent.measuredHeight}")

//        val holder = RvViewHolder(view)
//        holder.itemView.minimumWidth = parent.width / (ITEMS_PER_PAGE / 2)
//        holder.itemView.minimumHeight = parent.height / (ITEMS_PER_PAGE / 2)

        return RvViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(items[position])
    }
}