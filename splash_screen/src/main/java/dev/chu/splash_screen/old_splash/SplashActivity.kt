package dev.chu.splash_screen.old_splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import dev.chu.splash_screen.MainActivity
import dev.chu.splash_screen.R

class SplashActivity : AppCompatActivity() {

    companion object {
        private val SPLASH_TIME_OUT = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            // This method will be executed once the delay is over
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIME_OUT)
    }
}