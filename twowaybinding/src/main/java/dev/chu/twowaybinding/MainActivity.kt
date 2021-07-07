package dev.chu.twowaybinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelLazy
import dev.chu.twowaybinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by ViewModelLazy(
        MainViewModel::class,
        { viewModelStore },
        { defaultViewModelProviderFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
        }
        binding.includedLayout.textView2.text = mainViewModel.name
    }
}