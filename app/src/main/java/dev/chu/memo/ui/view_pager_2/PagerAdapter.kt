package dev.chu.memo.ui.view_pager_2

import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.chu.memo.etc.extension.TAG
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PagerItem(
    val id: Int,
    val title: String,
    val color: String,
    val value: Int
) : Parcelable

class PagerAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    private val items: ArrayList<List<PagerItem>> = arrayListOf()

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        Log.e(TAG, "position = $position")

        val itemList = arrayListOf<PagerItem>()
        items[position].forEach {
            itemList.add(it)
        }

        return PagerFragment.newInstance(itemList)
    }

    fun setNewItems(newItems: List<PagerItem>) {
        val list: MutableList<List<PagerItem>> = mutableListOf()
        for(i in 0..newItems.size / ITEMS_PER_PAGE) {
            if((i+1)*ITEMS_PER_PAGE >= newItems.size) {
                list.add(newItems.subList(i*ITEMS_PER_PAGE, newItems.size))
            } else {
                list.add(newItems.subList(i*ITEMS_PER_PAGE, (i+1)*ITEMS_PER_PAGE))
            }
        }

        list.forEachIndexed { index, items ->
            Log.e(TAG, "index = $index")
            items.forEach { item ->
                Log.e(TAG, "item = $item")
            }
        }

        items.clear()
        items.addAll(list)
        Log.e(TAG, "items = ${items.size}")
        notifyDataSetChanged()
    }
}