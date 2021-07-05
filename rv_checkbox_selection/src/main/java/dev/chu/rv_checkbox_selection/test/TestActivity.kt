package dev.chu.rv_checkbox_selection.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dev.chu.custom_gallery.etc.Const
import dev.chu.custom_gallery.util.permission.PermissionCheck
import dev.chu.rv_checkbox_selection.R
import dev.chu.rv_checkbox_selection.databinding.ActivityTestBinding

class TestActivity: AppCompatActivity() {

    private val viewModel: TestViewModel by lazy {
        ViewModelProvider(this)[TestViewModel::class.java]
    }

    private val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
//            binding.image.isVisible = true
            val byteArr = activityResult.data?.getByteArrayExtra(Const.IMAGE_BYTE_ARRAY) ?: return@registerForActivityResult
//            binding.image.setImageLoad(byteArray = byteArr)
        }
    }

    private val launcher = PermissionCheck.register(this) { mediaList ->
//        adapter.setNewItems(mediaList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityTestBinding>(this, R.layout.activity_test)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        launcher.launch()
    }
}