package dev.chu.memo.custom_ui

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.animation.LinearInterpolator
import androidx.annotation.Keep
import kotlin.math.roundToInt

/**
 * Alpha Mask Appearance
 */
class ClickDrawable2(layers: Array<out Drawable> = arrayOf()) : LayerDrawable(layers) {

    private val mask: Drawable =
        ColorDrawable(0xFF00FFFF.toInt()).apply { alpha = 0 }

    init {
        addLayer(mask)
    }

    private var wasPressed = false
    private var mActive = false
    private var canStart = true
    private var mBounds = bounds

    override fun isStateful(): Boolean {
        return true
    }

    @Keep
    private fun setMaskAlpha(alpha: Int) {
        mask.alpha = alpha
        invalidateSelf()
    }

    private fun colorMask(fromAlpha: Int, toAlpha: Int) {
        val anim = ObjectAnimator.ofInt(this, "maskAlpha", fromAlpha, toAlpha, fromAlpha)
        anim.duration = DURATION
        anim.interpolator = LinearInterpolator()
        anim.start()
    }

    private fun setClickActive(active: Boolean) {
        if (mActive != active) {
            mActive = active
            if (active && canStart) {
                canStart = false
                mBounds = copyBounds()
                startAnimation()
                colorMask(0, 64)
            }
        }
    }

    @Keep
    private fun setBounds(value: Float) {
        if (value == 1f) {
            canStart = true
        }
        val x = mBounds.width()
        val y = mBounds.height()
        val factorX = (x * value).roundToInt()
        val factorY = (y * value).roundToInt()
        bounds = mBounds.run {
            Rect(
                right - factorX,
                bottom - factorY,
                factorX,
                factorY
            )
        }
        invalidateSelf()
    }

    private fun startAnimation() {
        val anim = ObjectAnimator.ofFloat(this, "bounds", 0.99f, 0.95f, 1f)
        anim.duration = 225
        anim.interpolator = LinearInterpolator()
        anim.start()
    }

    private companion object {
        const val DURATION = 225L
    }
}