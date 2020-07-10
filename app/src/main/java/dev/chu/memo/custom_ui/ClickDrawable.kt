package dev.chu.memo.custom_ui

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.animation.LinearInterpolator
import kotlin.math.roundToInt


/**
 * https://medium.com/swlh/custom-drawable-25f56044e8dc
 *
 * Rounded Rectangle Bounce
 *
 * val drawable = ResourcesCompat.getDrawable(resources, R.drawable.background, null) as Drawable
 * image.background = ClickDrawable(arrayOf(FillDrawable(0xFF0000FF.toInt(), arrayOf(drawable))))
 */
class ClickDrawable(layers: Array<out Drawable> = arrayOf()) : LayerDrawable(layers) {

    private var wasPressed = false
    private var mActive = false
    private var canStart = true
    private var mBounds = bounds

    override fun isStateful(): Boolean {
        return true
    }

//    override fun onStateChange(stateSet: IntArray): Boolean {
//        val changed = super.onStateChange(stateSet)
//        var enabled = false
//        var pressed = false
//        var focused = false
//        var hovered = false
//        for (state in stateSet) {
//            if (state == R.attr.state_enabled) {
//                enabled = true
//            } else if (state == R.attr.state_focused) {
//                focused = true
//            } else if (state == R.attr.state_pressed) {
//                pressed = true
//            } else if (state == R.attr.state_hovered) {
//                hovered = true
//            }
//        }
//        setRippleActive(enabled && pressed)
//        setBackgroundActive(hovered, focused, pressed)
//        return changed
//    }
    // 관련 없는 코드 삭제 후 아래처럼 만든다.

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

        /**
         * 손가락을 떼면 애니메이션을 시작하고 싶다.
         * 그렇지 않으면, 롱 클릭은 bouncing drawable을 시작한다.
         * 시작 조건은 enabled 하고 pressed 하지만 더이상 pressed하지 않는 상태이다.
         */
        setClickActive(enabled && wasPressed && !pressed)

        wasPressed = pressed
        return super.onStateChange(state)
    }

    private fun setClickActive(active: Boolean) {
        if (mActive != active) {
            mActive = active
            if (active && canStart) {
                canStart = false
                mBounds = copyBounds()
                startAnimation()
            }
        }
    }

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
}