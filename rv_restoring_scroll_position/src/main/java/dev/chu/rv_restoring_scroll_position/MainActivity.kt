package dev.chu.rv_restoring_scroll_position

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.chu.extensions.toast
import dev.chu.rv_restoring_scroll_position.databinding.ActivityMainBinding
import dev.chu.rv_restoring_scroll_position.view.MainFragment

/**
 * https://cesarmorigaki.medium.com/restoring-scroll-position-of-nested-recyclerviews-8ad72b105294
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        toast(R.string.back_pressed_message)
    }
}

fun View.click(block: (View) -> Unit) {
    this.setOnClickListener(block)
}