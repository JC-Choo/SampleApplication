package dev.chu.vp2_sample.infinite

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dev.chu.vp2_sample.databinding.ActivityInfiniteVpBinding

class InfiniteVpActivity : AppCompatActivity() {

    private val binding: ActivityInfiniteVpBinding by lazy {
        ActivityInfiniteVpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.infiniteViewPager2.apply {
            setAdapter(InfiniteAdapter(listOf("First", "Second", "Third")))
            addScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        Log.d("Tracking", "I am at item index ${getCurrentItem()}")
                    }
                }
            })
        }
    }
}