package dev.chu.timer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.ContentViewCallback
import com.google.android.material.snackbar.Snackbar

/**
 * @param [message] : Snackbar 에서 보여줄 메시지
 * @param [duration] : Snackbar 를 보여줄 시간
 *      - LENGTH_INDEFINITE = -2 : 무기한으로 스낵바를 보여준다. dismiss 또는 다른 스낵바를 보여줄때 까지 보여준다.
 *      - LENGTH_SHORT = -1 : 짧은 시간 스낵바를 보여준다.
 *      - LENGTH_LONG = 0 : 긴 시간 스낵바를 보여준다.
 * @param [isAction] : action 을 설정할건지에 대한 여부
 * @param [actionText] : action 에 보여줄 텍스트
 * @param [listener] : Snackbar 클릭 시 이벤트
 */
fun View.snack(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    isAction: Boolean = false,
    actionText: String = "Dismiss",
    listener: View.OnClickListener? = View.OnClickListener { v -> Toast.makeText(v?.context, "click", Toast.LENGTH_SHORT).show() },
    isAnchorView: Boolean = false
) {
    val snack = Snackbar.make(this, message, duration)
    if (isAction) {
        // empty action will dismiss the snackbar
        snack.setAction(actionText, listener)
    }
    if (isAnchorView) {
        snack.anchorView = this
    }
    snack.show()
}

fun View.snack(@StringRes resId: Int, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(this, resId, duration).show()

/**
 * 참고 : https://vladsonkin.com/how-to-customize-android-snackbar/?utm_source=feedly&utm_medium=rss&utm_campaign=how-to-customize-android-snackbar
 *
 * [ContentViewCallback] : Snackbar 의 animation 을 담당하는 부분
 */
class IconSnackbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    lateinit var message: TextView

    init {
        View.inflate(context, R.layout.custom_snack_bar, this)
        message = findViewById(R.id.message)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        AlphaAnimation(0F, 1F).apply {
            interpolator = DecelerateInterpolator()
            setDuration(500)
        }.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        AlphaAnimation(1F, 0F).apply {
            interpolator = AccelerateInterpolator()
            setDuration(500)
        }.start()
    }
}

class IconSnackbar(
    parent: ViewGroup,
    content: IconSnackbarView
) : BaseTransientBottomBar<IconSnackbar>(parent, content, content) {

    companion object {
        fun make(viewGroup: ViewGroup, message: String, duration: Int): IconSnackbar {
            val customView = LayoutInflater.from(viewGroup.context).inflate(
                R.layout.layout_icon_snackbar,
                viewGroup,
                false
            ) as IconSnackbarView

            customView.message.text = message

            return IconSnackbar(viewGroup, customView).setDuration(duration)
        }
    }
}