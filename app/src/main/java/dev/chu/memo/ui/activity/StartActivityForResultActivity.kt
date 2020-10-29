package dev.chu.memo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import dev.chu.memo.ui.activity._new.ActivityResultSampleActivity
import dev.chu.memo.ui.activity._old.OldActivityResultSampleActivity

class StartActivityForResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView {
            add(::LinearLayout) {
                orientation = VERTICAL
                button(txt = "New ActivityResultContract", color = 0xFF81D4FA.toInt()) {
                    startActivity(Intent(context, ActivityResultSampleActivity::class.java))
                }

                button(txt = "Old Pattern") {
                    startActivity(Intent(context, OldActivityResultSampleActivity::class.java))
                }
            }
        }
    }
}