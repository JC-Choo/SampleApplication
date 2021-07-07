package dev.chu.twowaybinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.chu.twowaybinding.databinding.ActivityTwoWayBindingBinding

class TwoWayBindingActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTwoWayBindingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_two_way_binding)
        binding.lifecycleOwner = this
        binding.user = SampleUser()
    }
}