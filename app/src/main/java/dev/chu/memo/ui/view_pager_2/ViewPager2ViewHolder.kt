package dev.chu.memo.ui.view_pager_2

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R

class ViewPager2ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    private val tv: TextView = view.findViewById(R.id.title)
    private val rv: RecyclerView = view.findViewById(R.id.list)

    fun bind(items: List<String>) {
//        tv.text = item

        val rvAdapter = RvAdapter(items as MutableList<String>)
        with(rv) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = rvAdapter
        }
    }
}

class RvViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val tv: TextView = view.findViewById(R.id.title)

    fun bind(item: String) {
        tv.text = item
    }
}