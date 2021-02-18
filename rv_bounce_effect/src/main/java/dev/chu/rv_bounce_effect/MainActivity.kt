package dev.chu.rv_bounce_effect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_bounce_effect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = MyAdapter().apply {
                setNewItems(setNewItemList())
            }
            edgeEffectFactory = BounceEdgeEffectFactory()
        }
    }

    private fun setNewItemList(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until 20) {
            list.add("Item $i")
        }
        return list
    }
}