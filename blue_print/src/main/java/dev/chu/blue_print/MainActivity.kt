package dev.chu.blue_print

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * https://github.com/hongbeomi/Blueprint
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootView = findViewById<ConstraintLayout>(R.id.constraintLayout_root_sample)
        val button = findViewById<Button>(R.id.button_sample)

        button.setOnClickListener {
            val inspectorDialog = InspectorDialog(this, rootView)
            inspectorDialog.show()
        }
    }
}