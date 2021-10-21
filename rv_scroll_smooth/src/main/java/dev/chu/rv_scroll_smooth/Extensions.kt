package dev.chu.rv_scroll_smooth

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.view.View
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

fun ScrollView.scrollToView(view: View) {
    val y = computeDistanceToView(view)
    this.scrollTo(0, y)
}

fun ScrollView.smoothScrollToView(view: View) {
    val y = computeDistanceToView(view)
    ObjectAnimator.ofInt(this, "scrollY", y).apply {
        duration = 1000L // 스크롤이 지속되는 시간을 설정한다. (1000 밀리초 == 1초)
    }.start()
}

internal fun ScrollView.computeDistanceToView(view: View): Int {
    return abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
}

internal fun calculateRectOnScreen(view: View): Rect {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    return Rect(
        location[0],
        location[1],
        location[0] + view.measuredWidth,
        location[1] + view.measuredHeight
    )
}