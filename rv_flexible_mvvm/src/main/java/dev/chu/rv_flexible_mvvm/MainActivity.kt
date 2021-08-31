package dev.chu.rv_flexible_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dev.chu.rv_flexible_mvvm.databinding.ActivityMainBinding

/**
 * https://proandroiddev.com/flexible-recyclerview-adapter-with-mvvm-and-data-binding-74f75caef66a
 */
class MainActivity : AppCompatActivity() {

    private val viewModel: CarListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        setContentView(binding.root)
    }
}