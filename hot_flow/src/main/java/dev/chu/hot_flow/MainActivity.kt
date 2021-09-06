package dev.chu.hot_flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dev.chu.hot_flow.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect

/**
 * https://chao2zhang.medium.com/safely-collecting-hot-flows-from-android-native-ui-f22f645edb44
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plusOne.setOnClickListener {
            viewModel.increment()
        }

//
//        lifecycleScope.launchWhenResumed {
//            viewModel.sourceFlow.collect {
//                binding.sourceFlow.text = it.toString()   // Execution stops here
//            }
//            viewModel.transformedFlow.collect {
//                binding.transformedFlow.text = it.toString()
//            }
//        }

        /** 위 코드 대신 아래 코드 사용 */

        lifecycleScope.launchWhenResumed {
            viewModel.sourceFlow.collect {
                binding.sourceFlow.text = it.toString()
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.transformedFlow.collect {
                binding.transformedFlow.text = it.toString()
            }
        }
    }
}