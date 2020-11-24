package dev.chu.memo.ui.view_pager_2

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.etc.extension.TAG

//class ViewPager2ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
////    private val tv: TextView = view.findViewById(R.id.title)
//    private val rv: RecyclerView = view.findViewById(R.id.list)
//
//    fun bind(items: List<String>) {
////        tv.text = item
//
////        val rvAdapter = RvAdapter(items as MutableList<String>)
////        with(rv) {
////            layoutManager = GridLayoutManager(context, 2)
////            adapter = rvAdapter
////        }
//    }
//}

class RvViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val tv: TextView = view.findViewById(R.id.title)

    init {
        Log.e(TAG, "view.width = ${view.width}")
        Log.e(TAG, "view.height = ${view.height}")
        Log.e(TAG, "view.width = ${view.layoutParams.width}")
        Log.e(TAG, "view.height = ${view.layoutParams.height}")
        Log.e(TAG, "itemView.width = ${itemView.width}")
        Log.e(TAG, "itemView.height = ${itemView.height}")
    }

    fun bind(item: PagerItem) {
//        val data = "title = ${item.title}\nid = ${item.id}\nvalue = ${item.value}"
        val data = "id = ${item.id}"
        Log.e(TAG, "data = $data")
        tv.text = data
        tv.setTextColor(Color.parseColor(item.color))
    }
}