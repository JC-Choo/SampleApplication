package dev.chu.viewtouchevent

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import dev.chu.extensions.TAG

class TextView875 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): AppCompatTextView(context, attrs, defStyle) {
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TAG, "dispatchTouchEvent")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TAG, "onTouchEvent")
        return super.onTouchEvent(event)
    }
}