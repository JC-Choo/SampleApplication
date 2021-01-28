package dev.chu.touchevent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.motion.widget.MotionLayout

class MyMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): MotionLayout(context, attrs, defStyle) {

    var oX = 0F

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action

        when {
            action == MotionEvent.ACTION_DOWN -> {
                oX = ev.x
            }
            action == MotionEvent.ACTION_MOVE && ev.x > oX + 50 || ev.x < oX - 50 -> {
                return true
            }
        }

        return false
    }
}