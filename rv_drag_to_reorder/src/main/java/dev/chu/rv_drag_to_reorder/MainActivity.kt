package dev.chu.rv_drag_to_reorder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chu.rv_drag_to_reorder.databinding.ActivityMainBinding
import dev.chu.rv_drag_to_reorder.reorder.ReorderActivity
import dev.chu.rv_drag_to_reorder.swipe.SwipeToDismissActivity
import dev.chu.rv_drag_to_reorder.utils.click

/**
 * https://medium.com/better-programming/drag-to-reorder-android-recyclerview-items-using-kotlin-afcaee1b7fb5
 * https://github.com/SG-K/RecyclerviewWithBenefits
 */
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btReorder.click {
            startActivity(Intent(this, ReorderActivity::class.java))
        }

        binding.btSwipeToDismiss.click {
            startActivity(Intent(this, SwipeToDismissActivity::class.java))
        }
    }
}