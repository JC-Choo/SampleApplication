package dev.chu.memo.ui.fav_tv_shows

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityFavTvShowsBinding
import dev.chu.memo.etc.extension.TAG
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

// https://medium.com/better-programming/how-to-use-the-room-persistence-library-with-kotlin-flow-c73f461a0819

class FavTvShowsActivity : BaseActivity<ActivityFavTvShowsBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_fav_tv_shows

    private val viewModel by viewModel<FavTvShowsViewModel>()
    private val adapter by inject<FavTvShowsAdapter>()

    override fun initView(savedInstanceState: Bundle?) {
        Log.i(TAG, "initView")

        binding.favTvShowsRv.adapter = this.adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.observeMovies()
        }
        viewModel.addDataToRoomNew()

//        viewModel.getDataFromRoom().observe(this, Observer {
//            adapter.setItems(it)
//        })

        viewModel.listFavTvShows.observe(this, Observer {
            adapter.setItems(it)
        })
    }
}