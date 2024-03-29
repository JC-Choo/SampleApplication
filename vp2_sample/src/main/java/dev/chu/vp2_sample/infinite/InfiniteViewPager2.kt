package dev.chu.vp2_sample.infinite

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dev.chu.vp2_sample.R

class InfiniteViewPager2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {


    private val viewPager2: ViewPager2 by lazy {
        findViewById(R.id.view_pager_infinite)
    }

    private val internalRecyclerView by lazy {
        viewPager2.getChildAt(0) as RecyclerView
    }

    private var totalItemCount = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.just_viewpager2, this, true)
    }

    fun <T : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<T>) {
        viewPager2.adapter = adapter
        viewPager2.setCurrentItem(1, false)
        totalItemCount = adapter.itemCount

        internalRecyclerView.apply {
            addOnScrollListener(
                InfiniteScrollBehaviour(
                    totalItemCount,
                    layoutManager as LinearLayoutManager
                )
            )
        }
    }

    /**
     *                        Auto move over to actual First View when the we reach the Fake First View
     *                        ______________________________________________
     *                       |                                              |
     *                       v                                              |
     * Fake Last View <-> First View <-> Second View <-> Last View <-> Fake First View
     *      |                                                ^
     *      |________________________________________________|
     *      Auto move over to actual Last View when we reach the Fack Last View
     */
    inner class InfiniteScrollBehaviour(
        private val itemCount: Int,
        private val layoutManager: LinearLayoutManager
    ) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
            val lastItemVisible = layoutManager.findLastVisibleItemPosition()

            if (firstItemVisible == (itemCount - 1) && dx > 0) {
                recyclerView.scrollToPosition(1)
            } else if (lastItemVisible == 0 && dx < 0) {
                recyclerView.scrollToPosition(itemCount - 2)
            }
        }
    }

    /**
     *          Fake Last View <-> First View <-> Second View <-> Last View <-> Fake First View
     * index :        0                1                2             3          ItemCount - 1
     * Actual = ItemCount - 3        Index-1        Index-1        Index-1             0
     *
     * count 에서 두개의 facked item 을 제거해야만 하기 때문에 ItemCount - 3 이고, 0 인덱스 기반 계산으로 정규화하려면 -1 이다.
     */
    fun getCurrentItem(): Int {
        return when (viewPager2.currentItem) {
            0 -> totalItemCount - viewPager2.adapter!!.itemCount
            totalItemCount - 1 -> 0
            else -> viewPager2.currentItem - 1
        }
    }

    fun addScrollListener(scrollListener: RecyclerView.OnScrollListener) {
        internalRecyclerView.addOnScrollListener(scrollListener)
    }

    fun removeScrollListener(scrollListener: RecyclerView.OnScrollListener) {
        internalRecyclerView.removeOnScrollListener(scrollListener)
    }
}