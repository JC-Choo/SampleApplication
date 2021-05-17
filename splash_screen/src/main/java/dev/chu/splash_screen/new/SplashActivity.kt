package dev.chu.splash_screen.new

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chu.splash_screen.MainActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doFirstRunCheckup()
    }

    private fun doFirstRunCheckup() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}