package dev.chu.mvvmarchitecture.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dev.chu.extensions.NetworkUtil
import dev.chu.extensions.TAG
import dev.chu.mvvmarchitecture.api.Api
import dev.chu.mvvmarchitecture.data.MainRepository
import dev.chu.mvvmarchitecture.data.local.CatDatabase
import dev.chu.mvvmarchitecture.data.local.LocalDataSource
import dev.chu.mvvmarchitecture.data.remote.RemoteDataSource
import dev.chu.mvvmarchitecture.databinding.ActivityMainBinding
import dev.chu.mvvmarchitecture.etc.ViewModelFactory

/**
 * https://medium.com/student-technical-community-vit-vellore/mvvm-with-room-db-and-retrofit-64c62c002591
 */
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val repository: MainRepository by lazy {
        val database = CatDatabase.getInstance(this)
        val localDataSource = LocalDataSource(database)
        val remoteDataSource = RemoteDataSource(Api.createApi())
        MainRepository(localDataSource, remoteDataSource)
    }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(repository))[MainViewModel::class.java]
    }

    private val adapter: MainAdapter by lazy {
        MainAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handledNetwork()
        setRecyclerView()
        observeViewModel()

        viewModel.fetchData { list ->
            list?.let {
                viewModel.insert(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setRecyclerView() {
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.setHasFixedSize(true)
    }

    private fun observeViewModel() {
        viewModel.getAllCats().observe(this) {
            adapter.setNewItems(it)
        }
    }

    private fun handledNetwork() {
        NetworkUtil.getNetworkLiveData(this).observe(this) {
            Log.i(TAG, "isConnect = $it")
        }
    }
}