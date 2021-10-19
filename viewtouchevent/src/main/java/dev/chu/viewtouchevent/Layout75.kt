package dev.chu.viewtouchevent

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import dev.chu.extensions.TAG

class Layout75 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG, "dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    /**
     * true 값 반환 시 자식에게 터치 이벤트 전달을 하지 않음
     * 즉, return true 적용 시 TextView875 의 터치 이벤트는 반응하지 않는다.
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG, "onInterceptTouchEvent")
        return super.onInterceptTouchEvent(ev)
//        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TAG, "onTouchEvent")
        return super.onTouchEvent(event)
    }
}