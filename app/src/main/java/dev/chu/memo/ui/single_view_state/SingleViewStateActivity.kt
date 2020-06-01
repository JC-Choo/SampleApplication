package dev.chu.memo.ui.single_view_state

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chu.memo.R

// https://zsmb.co/designing-and-working-with-single-view-states-on-android/

class SingleViewStateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_view_state)
    }
}