package dev.chu.touchevent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

class MyConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        /**
         * If the onInterceptTouchEvent returns FALSE,
         * then the event goes to the child who will process this event,
         * if TRUE then processes the ViewGroup itself on which the onInterceptTouchEvent is redefined,
         * since we return TRUE to all events, thereby intercepting all events and the child does not get anything.
         *
         * return false 일 경우, 이벤트는 이벤트를 처리할 child에게 간다.
         * return true 일 경우, ViewGroup이 onInterceptTouchEvent 를 재정의해 모든 이벤트를 true 로 return 한다.
         */
        return true
    }
}