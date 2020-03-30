package dev.chu.memo.ui.mvi

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import dev.chu.memo.R
import dev.chu.memo.databinding.ActivityMviBinding
import dev.chu.memo.etc.extension.showToast
import dev.chu.memo.ui.mvi.aac.AacMviActivity
import dev.chu.memo.ui.rv_coroutine.UserAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

// 참고 : https://proandroiddev.com/best-architecture-for-android-mvi-livedata-viewmodel-71a3a5ac7ee3

class MviActivity : AacMviActivity<MainViewState, MainViewEffect, MainViewEvent, MviActViewModel>() {
    // gradle에 activity.ktx 추가 시 발생하는 함수(viewModels)
    override val viewModel by viewModel<MviActViewModel>()
    private val userAdapter by inject<UserAdapter>()

    private lateinit var binding: ActivityMviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvi)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.adapter = userAdapter

        binding.srlUsersHome.setOnRefreshListener {
            viewModel.process(MainViewEvent.OnSwipeRefresh)
        }
    }

    override fun renderViewState(viewState: MainViewState) {
        when (viewState.fetchStatus) {
            is FetchStatus.Fetched -> {
                binding.srlUsersHome.isRefreshing = false
            }
            is FetchStatus.NotFetched -> {
                viewModel.process(MainViewEvent.FetchUsers)
                binding.srlUsersHome.isRefreshing = false
            }
            is FetchStatus.Fetching -> {
                binding.srlUsersHome.isRefreshing = true
            }
        }
        userAdapter.submitList(viewState.userList)
    }

    override fun renderViewEffect(viewEffect: MainViewEffect) {
        when (viewEffect) {
            is MainViewEffect.ShowSnackbar -> {
                Snackbar.make(binding.coordinatorLayoutRoot, viewEffect.message, Snackbar.LENGTH_SHORT).show()
            }
            is MainViewEffect.ShowToast -> {
                showToast(viewEffect.message)
            }
        }
    }

    fun onClickFab() {
        viewModel.process(MainViewEvent.FabClicked)
    }
}