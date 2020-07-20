package dev.chu.memo.ui.heart_animation

import android.graphics.drawable.Animatable2
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import dev.chu.memo.R
import kotlinx.android.synthetic.main.activity_heart_animation.*

// 참고 : https://blog.stylingandroid.com/animatedicons-heart/?utm_source=feedly&utm_medium=rss&utm_campaign=animatedicons-heart

class HeartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_animation)
    }

    override fun onResume() {
        super.onResume()

        container.children()
            .filterIsInstance<ImageView>()
            .forEach {
                it.addAnimationStarter()
            }
    }

    private fun ImageView.addAnimationStarter() =
        setOnClickListener {
            if (isRunning()) {
                stop()
            } else {
                start()
            }
        }

    private fun ViewGroup.children() =
        (0 until childCount).map { getChildAt(it) }

    private fun ImageView.start() {
        (drawable as? Animatable2)?.start()
    }

    private fun ImageView.isRunning(): Boolean =
        (drawable as? Animatable2)?.isRunning ?: false

    override fun onPause() {
        super.onPause()

        container.children()
            .filterIsInstance<ImageView>()
            .forEach { it.stop() }
    }

    private fun ImageView.stop() {
        (drawable as? Animatable2)?.stop()
    }
}