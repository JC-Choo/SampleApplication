package dev.chu.rv_scroll_smooth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.chu.rv_scroll_smooth.data.UserProvider
import dev.chu.rv_scroll_smooth.databinding.ActivityVerticalBinding

class VerticalScrollActivity: AppCompatActivity() {
    private val binding: ActivityVerticalBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_vertical)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        with(binding.list) {
            adapter = UserListAdapter(getDummyDataList())
        }

        binding.floating.setOnClickListener {

        }
    }

    private fun getDummyDataList() = UserProvider.getUserList()
}