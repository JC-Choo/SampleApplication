package dev.chu.rv_shimmer_effect

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.extensions.toast
import dev.chu.rv_shimmer_effect.data.Resource
import dev.chu.rv_shimmer_effect.databinding.ActivityMainBinding

/**
 * https://emre-arslan.medium.com/shimmer-effect-in-android-2b6840cc0097
 */
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private lateinit var viewModel: MainViewModel
    private var adapter: UserListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRecyclerView()
        observeViewModel()
        viewModel.getUserList()
    }

    private fun observeViewModel() {
        viewModel.apply {
            userListObserver.observe(this@MainActivity, Observer { status ->
                when (status) {
                    is Resource.Loading -> {
                        binding.shimmerLayout.startShimmer()
                    }
                    is Resource.Success -> {
                        status.data?.let {
                            adapter?.swapData(it)
                            showRecyclerView()
                        }
                    }
                    is Resource.Error -> {
                        toast("Error")
                    }
                }
            })
        }
    }

    private fun initRecyclerView() {
        adapter = UserListAdapter(emptyList())
        binding.userListRecyclerview.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.userListRecyclerview.adapter = adapter
        binding.userListRecyclerview.setHasFixedSize(true)
    }

    private fun showRecyclerView() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.userListRecyclerview.visibility = View.VISIBLE
    }
}