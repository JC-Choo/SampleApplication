package dev.chu.memo.ui.rv_sticky_header.test01

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import dev.chu.memo.R
import dev.chu.memo.etc.extension.TAG
import kotlin.math.max

interface SectionCallback {
    fun isSection(position: Int): Boolean
    fun getSectionHeader(position: Int): CharSequence
}

class RecyclerSectionItemDecoration(
    private val headerOffset: Int,
    private val sticky: Boolean,
    @param:NonNull private val sectionCallback: SectionCallback
) : ItemDecoration() {

    private var headerView: View? = null
    private var header: TextView? = null
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)
        if (sectionCallback.isSection(pos)) {
            outRect.top = headerOffset
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (headerView == null) {
            headerView = inflateHeaderView(parent)
            header = headerView?.findViewById(R.id.list_item_section_text)
            fixLayoutSize(headerView!!, parent)
        }
        var previousHeader: CharSequence = ""
//        for (i in 0 until parent.childCount) {
            val child: View = parent.getChildAt(3)
//            val position = parent.getChildAdapterPosition(child)
            val title = sectionCallback.getSectionHeader(3)

            Log.e(TAG, "parent.childCount = ${parent.childCount}, previousHeader = $previousHeader, position = $3, title = $title, ${sectionCallback.isSection(3)}")

            header!!.text = "title"
//            if (previousHeader != title || sectionCallback.isSection(position)) {
//                drawHeader(c, child, headerView)
//                previousHeader = title
//            }
            if (sectionCallback.isSection(3)) {
                drawHeader(c, child, headerView)
//                previousHeader = title
            }
//        }
    }

    private fun drawHeader(c: Canvas, child: View, headerView: View?) {
        c.save()
        if (sticky) {
            c.translate(0f, max(0, child.top - headerView!!.height).toFloat())
        } else {
            c.translate(0f, (child.top - headerView!!.height).toFloat())
        }
        headerView.draw(c)
        c.restore()
    }

    private fun inflateHeaderView(parent: RecyclerView): View {
        return LayoutInflater.from(parent.context)
            .inflate(
                R.layout.recycler_section_header,
                parent,
                false
            )
    }

    /**
     * Measures the header view to make sure its size is greater than 0 and will be drawn
     * https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations
     */
    private fun fixLayoutSize(view: View, parent: ViewGroup) {
        val widthSpec: Int = View.MeasureSpec.makeMeasureSpec(
            parent.width,
            View.MeasureSpec.EXACTLY
        )
        val heightSpec: Int = View.MeasureSpec.makeMeasureSpec(
            parent.height,
            View.MeasureSpec.UNSPECIFIED
        )
        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )
        view.measure(
            childWidth,
            childHeight
        )
        view.layout(
            0,
            0,
            view.measuredWidth,
            view.measuredHeight
        )
    }
}
