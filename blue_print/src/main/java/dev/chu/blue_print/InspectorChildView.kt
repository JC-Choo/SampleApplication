package dev.chu.blue_print

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

class InspectorChildView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var bitmap: Bitmap? = null
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.darker_gray)
        style = Paint.Style.STROKE
        strokeWidth = resources.getDimensionPixelSize(R.dimen.dp_2).toFloat()
    }

    fun setInfo(info: InspectorInfo) {
        layoutParams = ViewGroup.LayoutParams(
            info.width,
            info.height
        ).apply {
            x = info.offsetX.toFloat()
            y = info.offsetY.toFloat()
        }
        bitmap = info.bitmap
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        bitmap?.let {
            canvas?.drawBitmap(it, 0f, 0f, null)
        }

        // 배경 선 그리기
        canvas?.drawRoundRect(
            0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), 0f, 0f, paint
        )
    }
}