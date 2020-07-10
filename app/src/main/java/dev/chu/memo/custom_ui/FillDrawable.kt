package dev.chu.memo.custom_ui

import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.animation.LinearInterpolator
import androidx.core.graphics.toRectF

/**
 * Long-Click Filling Color
 */
class FillDrawable(private val color: Int, layers: Array<out Drawable>) : LayerDrawable(layers) {

    private val radius: Float = 20f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = this@FillDrawable.color
    }

    private var r = copyBounds()

    private var activeState = false

    override fun isStateful(): Boolean = true

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        val b = requireNotNull(bounds)
        r = Rect(b.left, b.top, b.left + (b.right * 0.1).toInt(), b.bottom)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawRoundRect(r.toRectF(), radius, radius, paint)
    }

    override fun onStateChange(state: IntArray?): Boolean {
        val stateSet = state?.toSet() ?: emptySet()

        var enabled = false
        var pressed = false
        for (s in stateSet) {
            when (s) {
                android.R.attr.state_enabled -> enabled = true
                android.R.attr.state_pressed -> pressed = true
            }
        }

        setActive(enabled && pressed)

        return super.onStateChange(state)
    }

    private fun setActive(active: Boolean) {
        if (activeState != active) {
            activeState = active
            if (active) {
                extendsWidth()
            } else {
                anim.cancel()
            }
        }
    }

    private fun setWidth(factor: Float) {
        r = Rect(
            bounds.left,
            bounds.top,
            (bounds.right * factor).toInt(),
            bounds.bottom
        )
        invalidateSelf()
    }

    private val anim = ObjectAnimator.ofFloat(this, "width", 0.1f, 1f)
    private fun extendsWidth() {
        anim.startDelay = 100
        anim.duration = 300
        anim.interpolator = LinearInterpolator()
        anim.start()
    }
}