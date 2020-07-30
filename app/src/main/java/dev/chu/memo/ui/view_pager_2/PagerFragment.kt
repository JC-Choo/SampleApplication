package dev.chu.memo.ui.view_pager_2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dev.chu.memo.R
import kotlinx.android.synthetic.main.fragment_pager.*
import java.util.*

class PagerFragment : Fragment(R.layout.fragment_pager) {

    private lateinit var rvAdapter: RvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = requireArguments().getParcelableArrayList<PagerItem>(EXTRA_PAGER_ITEM_LIST)
        rvAdapter = RvAdapter()

        with(list) {
            adapter = rvAdapter

            layoutManager = GridLayoutManager(requireContext(), ITEMS_PER_PAGE/2, GridLayoutManager.HORIZONTAL, false)
            val snapToBlock = SnapToBlock(ITEMS_PER_PAGE)
            snapToBlock.attachToRecyclerView(list)
            snapToBlock.setSnapBlockCallback(object : SnapToBlock.SnapBlockCallback {
                override fun onBlockSnap(snapPosition: Int) {
//                    if (selectedItemPos == snapPosition)
//                        return
//                    selectedItemPos = snapPosition
//                    (list.adapter as VpAdapter).setNewPosition(selectedItemPos)
//                (list.adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>).notifyDataSetChanged()
                }

                override fun onBlockSnapped(snapPosition: Int) {
//                    if (selectedItemPos == snapPosition)
//                        return
//                    selectedItemPos = snapPosition
//                    (list.adapter as VpAdapter).setNewPosition(selectedItemPos)
//                (list.adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>).notifyDataSetChanged()
                }
            })
        }
        rvAdapter.setNewItems(items ?: mutableListOf())



//        txtTitle.text = requireArguments().getString(EXTRA_TITLE)
//        view.setBackgroundColor(Color.parseColor(requireArguments().getString(EXTRA_COLOR)))
//        setValue(requireArguments().getInt(EXTRA_VALUE))
    }

//    fun setValue(newValue: Int) {
////        txtValue.text = newValue.toString()
//    }

    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_COLOR = "color"
        private const val EXTRA_VALUE = "value"
        private const val EXTRA_PAGER_ITEM_LIST = "list"

        fun newInstance(item: ArrayList<PagerItem>): PagerFragment {
            return PagerFragment().apply {
                arguments = Bundle().apply {
//                    putString(EXTRA_TITLE, item.title)
//                    putString(EXTRA_COLOR, item.color)
//                    putInt(EXTRA_VALUE, item.value)
                    putParcelableArrayList(EXTRA_PAGER_ITEM_LIST, item)
                }
            }
        }
    }
}