package dev.chu.memo.ui.view_pager_2

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import kotlinx.android.synthetic.main.activity_view_pager_2.*

class ViewPager2Activity : AppCompatActivity() {
    val USE_GRID = true
    //        val USE_GRID = true
    val ITEMS_PER_PAGE = 4
    var selectedItemPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_2)

        val inflater = LayoutInflater.from(this)
        list.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val textView = holder.itemView as TextView
                textView.setBackgroundColor(if (position % 2 == 0) 0xffff0000.toInt() else 0xff00ff00.toInt())
                textView.text = if (selectedItemPos == position) "selected: $position" else position.toString()
            }

            override fun getItemCount(): Int {
                return 15
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
                view.layoutParams.width =
                    if (USE_GRID)
                        list.width / (ITEMS_PER_PAGE / 2)
                    else
                        list.width / 4
                view.layoutParams.height = list.height / (ITEMS_PER_PAGE / 2)
                view.gravity = Gravity.CENTER
                return object : RecyclerView.ViewHolder(view) {
                }
            }
        }
        list.layoutManager =
            if (USE_GRID)
                GridLayoutManager(this, ITEMS_PER_PAGE / 2, GridLayoutManager.HORIZONTAL, false)
            else
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapToBlock = SnapToBlock(ITEMS_PER_PAGE)
        snapToBlock.attachToRecyclerView(list)
        snapToBlock.setSnapBlockCallback(object : SnapToBlock.SnapBlockCallback {
            override fun onBlockSnap(snapPosition: Int) {
                if (selectedItemPos == snapPosition)
                    return
                selectedItemPos = snapPosition
                (list.adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>).notifyDataSetChanged()
            }

            override fun onBlockSnapped(snapPosition: Int) {
                if (selectedItemPos == snapPosition)
                    return
                selectedItemPos = snapPosition
                (list.adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>).notifyDataSetChanged()
            }

        })
    }
}