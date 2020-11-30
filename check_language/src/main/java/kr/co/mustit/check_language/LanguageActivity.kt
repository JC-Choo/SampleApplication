package kr.co.mustit.check_language

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LanguageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        findViewById<Button>(R.id.enBt).setOnClickListener {
            MainApplication.get().setPrefString("language", "en")
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<Button>(R.id.koBt).setOnClickListener {
            MainApplication.get().setPrefString("language", "ko")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}