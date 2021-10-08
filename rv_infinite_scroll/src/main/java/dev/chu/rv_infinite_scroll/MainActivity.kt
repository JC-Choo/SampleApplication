package dev.chu.rv_infinite_scroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_infinite_scroll.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
/**
 * https://proandroiddev.com/infinite-auto-scrolling-lists-with-recyclerview-lazylists-in-compose-1c3b44448c8
 * ??? 나중에 컴포트로 해보자...
 *
 *
 * 무한 자동 스크롤인데 아이템 마다 튕긴다.. 굳이 ?
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_BETWEEN_SCROLL_MS = 25L
        private const val SCROLL_DX = 5
        private const val DIRECTION_RIGHT = 1
    }

    private val adapter by lazy { FeaturesAdapter() }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val featureList = ArrayList<Feature>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        getFeatureList()
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapter.submitList(featureList)
        lifecycleScope.launch {
            autoScrollFeaturesList()
        }
    }

    private fun getFeatureList() = (0 .. 15).forEach {
        featureList.add(Feature(R.drawable.ic_launcher_foreground, "${it}번"))
    }

    private tailrec suspend fun autoScrollFeaturesList() {
        if (binding.list.canScrollHorizontally(DIRECTION_RIGHT)) {
            binding.list.smoothScrollBy(SCROLL_DX, 0)
        } else {
            val firstPosition =
                (binding.list.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (firstPosition != RecyclerView.NO_POSITION) {
                val currentList = adapter.currentList
                val secondPart = currentList.subList(0, firstPosition)
                val firstPart = currentList.subList(firstPosition, currentList.size)
                adapter.submitList(firstPart + secondPart)
            }
        }
        delay(DELAY_BETWEEN_SCROLL_MS)
        autoScrollFeaturesList()
    }
}

