package dev.chu.rv_scroll_smooth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

/**
 * https://greedy0110.tistory.com/41
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.vertical).setOnClickListener {
            startActivity(Intent(this, VerticalScrollActivity::class.java))
        }
    }
}