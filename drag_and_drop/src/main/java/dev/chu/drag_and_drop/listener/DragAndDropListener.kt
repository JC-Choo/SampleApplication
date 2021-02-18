package dev.chu.drag_and_drop.listener

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.DragEvent
import android.view.View
import androidx.core.content.ContextCompat
import dev.chu.drag_and_drop.R

/**
 * 드래그 가능한 뷰의 가시성을 업데이트하기 위한 콜백
 */
class DragListener : View.OnDragListener {
    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        val view = v ?: return true
        val dragEvent = event ?: return true

        when (dragEvent.action) {
            // 드래그가 시작될 때 view 를 숨긴다.
            DragEvent.ACTION_DRAG_ENTERED -> view.visibility = View.INVISIBLE

            // 드래그가 끝났을 때 view 를 보여준다.
            DragEvent.ACTION_DRAG_ENDED -> view.visibility = View.VISIBLE
        }

        return true
    }
}

/**
 * 드래그된 아이템이 드랍될 목표 뷰에 대한 콜백
 */
class DropListener(
    private val onDrop: () -> Unit
) : View.OnDragListener {
    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        val view = v ?: return true
        val dragEvent = event ?: return true

        when (dragEvent.action) {
            // 드랍 영역의 테두리를 보여주기 위해 view 아래 배경을 애니메이션하고 하이라이트 한다.
            DragEvent.ACTION_DRAG_ENTERED -> {
                val bgColor = ContextCompat.getColor(view.context, R.color.colorWhiteTransparent)
                if (view.background is ColorDrawable &&
                    (view.background as ColorDrawable).color == bgColor
                ) {
                    return true
                }

                ValueAnimator.ofArgb(Color.TRANSPARENT, bgColor).apply {
                    addUpdateListener {
                        val color = it.animatedValue as Int
                        view.setBackgroundColor(color)
                    }
                    duration = 500
                }.start()
            }

            // 드랍 영역 아래에 하이라이트(강조)를 애니메이션하고 숨긴다.
            DragEvent.ACTION_DRAG_ENDED -> {
                val bgColor = ContextCompat.getColor(view.context, R.color.colorWhiteTransparent)
                if (view.background is ColorDrawable &&
                    (view.background as ColorDrawable).color == Color.TRANSPARENT
                ) {
                    return true
                }

                ValueAnimator.ofArgb(bgColor, Color.TRANSPARENT).apply {
                    addUpdateListener {
                        val color = it.animatedValue as Int
                        view.setBackgroundColor(color)
                    }
                    duration = 500
                }.start()
            }

            // 아이템이 드랍됐을 때, 드랍에 대해 알린다.
            DragEvent.ACTION_DROP -> onDrop()
        }

        return true
    }
}