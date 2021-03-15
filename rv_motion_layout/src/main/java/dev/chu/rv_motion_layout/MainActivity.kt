package dev.chu.rv_motion_layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_motion_layout.databinding.ActivityMainBinding

/**
 * 참고 : https://mjmanaog.medium.com/motion-layout-creating-simple-recycler-view-animation-e55d5c33ca6e
 */
class MainActivity : AppCompatActivity() {

    private val foodAdapter: FoodAdapter = FoodAdapter()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMain.adapter = foodAdapter
        foodAdapter.setNewItems(foodDummyData)
    }

}