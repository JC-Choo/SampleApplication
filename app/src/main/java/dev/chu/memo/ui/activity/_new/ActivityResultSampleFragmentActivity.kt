package dev.chu.memo.ui.activity._new

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dev.chu.memo.ui.activity.add
import dev.chu.memo.ui.activity.setContentView

class ActivityResultSampleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootViewId = View.generateViewId()
        setContentView {
            add(::FrameLayout) {
                id = rootViewId
            }
        }

        supportFragmentManager.commit(true) {
            add(rootViewId, ActivityResultSampleFragment())
        }
    }
}