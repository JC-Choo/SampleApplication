//package dev.chu.memo.ui.rv_sticky_header.test02
//
//import androidx.annotation.NonNull
//import androidx.annotation.Nullable
//import androidx.recyclerview.widget.RecyclerView
//import dev.chu.memo.ui.rv_sticky_header.test02.StickHeaderItemDecoration.StickyHeaderInterface
//
//abstract class StickyHeaderRecyclerView<D : StickyMainData?, H : HeaderData?> :
//    RecyclerView.Adapter<Any>(), StickyHeaderInterface {
//
//    private var mData: MutableList<StickyMainData> = mutableListOf()
//
//    override fun onAttachedToRecyclerView(@NonNull recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        val stickHeaderDecoration = StickHeaderItemDecoration(this)
//        recyclerView.addItemDecoration(stickHeaderDecoration)
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (mData[position] is HeaderData) {
//            (mData[position] as HeaderData).headerType
//        } else getViewType(position)
//    }
//
//    override fun isHeader(itemPosition: Int): Boolean {
//        return mData[itemPosition] is HeaderData
//    }
//
//    override fun getItemCount(): Int {
//        return mData.size
//    }
//
//    override fun getHeaderLayout(headerPosition: Int): Int {
//        return (mData[headerPosition] as HeaderData).headerLayout
//    }
//
//    override fun getHeaderPositionForItem(itemPosition: Int): Int {
//        var itemPosition = itemPosition
//        var headerPosition = 0
//        do {
//            if (isHeader(itemPosition)) {
//                headerPosition = itemPosition
//                break
//            }
//            itemPosition -= 1
//        } while (itemPosition >= 0)
//        return headerPosition
//    }
//
//    fun setHeaderAndData(
//        @NonNull datas: List<D>?,
//        @Nullable header: HeaderData?
//    ) {
//        if (header != null) {
//            mData.add(header)
//        }
//        mData.addAll(datas)
//    }
//
//    protected fun getViewType(pos: Int): Int {
//        return 0
//    }
//
//    protected fun getDataInPosition(position: Int): D {
//        return mData[position] as D
//    }
//
//    protected fun getHeaderDataInPosition(position: Int): H {
//        return mData[position] as H
//    }
//}