package dev.chu.memo.ui.view_pager_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import dev.chu.memo.R
import kotlinx.android.synthetic.main.activity_view_pager_2.*

const val ITEMS_PER_PAGE = 4

class ViewPager2Activity: AppCompatActivity() {

    companion object {

        private const val ITEMS_COUNT = 10
        private val colors = listOf(
            "#e53935",
            "#d81b60",
            "#8e24aa",
            "#2196f3",
            "#df78ef",
            "#d3b8ae"
        )
    }

    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_2)

        pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter
        pagerAdapter.setNewItems(generatePagerItems())

        TabLayoutMediator(indicator, viewPager) { tab, position ->

        }.attach()
    }

    private fun generatePagerItems(): List<PagerItem> {
        return (1..ITEMS_COUNT).map {
            val color = if(it < colors.size) it else it % colors.size
            PagerItem(it, "Item $it", colors[color], 0)
        }.toMutableList()
    }
}





//val USE_GRID = true
////        val USE_GRID = true
//val ITEMS_PER_PAGE = 4
//var selectedItemPos = 0
//
//class ViewPager2Activity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_pager_2)
//
////        val inflater = LayoutInflater.from(this)
//        list.adapter =
//            VpAdapter(selectedItemPos)
////            object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
////                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
////                    val textView = holder.itemView as TextView
////                    textView.setBackgroundColor(if (position % 2 == 0) 0xffff0000.toInt() else 0xff00ff00.toInt())
////                    textView.text = if (selectedItemPos == position) "selected: $position" else position.toString()
////                }
////
////                override fun getItemCount(): Int {
////                    return 15
////                }
////
////                override fun onCreateViewHolder(
////                    parent: ViewGroup,
////                    viewType: Int
////                ): RecyclerView.ViewHolder {
////                    val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
////                    view.layoutParams.width =
////                        if (USE_GRID)
////                            list.width / (ITEMS_PER_PAGE / 2)
////                        else
////                            list.width / 4
////                    view.layoutParams.height = list.height / (ITEMS_PER_PAGE / 2)
////                    view.gravity = Gravity.CENTER
////                    return object : RecyclerView.ViewHolder(view) {}
////                }
////            }
//        list.layoutManager =
//            if (USE_GRID)
//                GridLayoutManager(this, ITEMS_PER_PAGE / 2, GridLayoutManager.HORIZONTAL, false)
//            else
//                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        val snapToBlock = SnapToBlock(ITEMS_PER_PAGE)
//        snapToBlock.attachToRecyclerView(list)
//        snapToBlock.setSnapBlockCallback(object : SnapToBlock.SnapBlockCallback {
//            override fun onBlockSnap(snapPosition: Int) {
//                if (selectedItemPos == snapPosition)
//                    return
//                selectedItemPos = snapPosition
//                (list.adapter as VpAdapter).setNewPosition(selectedItemPos)
////                (list.adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>).notifyDataSetChanged()
//            }
//
//            override fun onBlockSnapped(snapPosition: Int) {
//                if (selectedItemPos == snapPosition)
//                    return
//                selectedItemPos = snapPosition
//                (list.adapter as VpAdapter).setNewPosition(selectedItemPos)
////                (list.adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>).notifyDataSetChanged()
//            }
//
//        })
//    }
//}
//
//class VpAdapter(
//    private var selectedItemPos: Int
//): RecyclerView.Adapter<VpViewHolder>() {
//
//    fun setNewPosition(position: Int) {
//        selectedItemPos = position
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): VpViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager_2_rv, parent, false)
//        view.layoutParams.width =
//            if (USE_GRID)
//                parent.width / (ITEMS_PER_PAGE / 2)
//            else
//                parent.width / 4
//        view.layoutParams.height = parent.height / (ITEMS_PER_PAGE / 2)
//        return VpViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return 15
//    }
//
//    override fun onBindViewHolder(holder: VpViewHolder, position: Int) {
//        holder.textView.text = if (selectedItemPos == position) "selected: $position" else position.toString()
//        holder.imageView.setBackgroundColor(if (position % 2 == 0) 0xffff0000.toInt() else 0xff00ff00.toInt())
//    }
//}
//
//class VpViewHolder(view: View): RecyclerView.ViewHolder(view) {
//    val textView: TextView = view.findViewById(R.id.title)
//    val imageView: ImageView = view.findViewById(R.id.image)
//}