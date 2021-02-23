package dev.chu.vp2_sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chu.vp2_sample.databinding.ActivityMainBinding
import dev.chu.vp2_sample.infinite.InfiniteVpActivity

/**
 * https://medium.com/mobile-app-development-publication/android-bi-direction-infinite-viewpager-2-scrolling-1a729e4ee773
 */
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btInfiniteVp.setOnClickListener {
            startActivity(Intent(this, InfiniteVpActivity::class.java))
        }
    }
}