package dev.chu.twowaybinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelLazy
import dev.chu.twowaybinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by ViewModelLazy(
        MainViewModel::class,
        { viewModelStore },
        { defaultViewModelProviderFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).run {
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
            setUpBinding(this)
        }
    }

    private fun setUpBinding(binding: ActivityMainBinding) {
        with(binding) {
            includedLayout.textView2.text = mainViewModel.name
        }
    }
}