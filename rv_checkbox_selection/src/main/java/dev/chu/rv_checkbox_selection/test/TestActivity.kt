package dev.chu.rv_checkbox_selection.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dev.chu.rv_checkbox_selection.R
import dev.chu.rv_checkbox_selection.databinding.ActivityTestBinding

class TestActivity: AppCompatActivity() {

    private val viewModel: TestViewModel by lazy {
        ViewModelProvider(this)[TestViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityTestBinding>(this, R.layout.activity_test)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}