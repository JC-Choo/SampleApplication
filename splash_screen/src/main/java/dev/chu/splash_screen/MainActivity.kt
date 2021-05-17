package dev.chu.splash_screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * https://medium.com/nerd-for-tech/modern-splash-screen-in-android-9c804903c7c9
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}