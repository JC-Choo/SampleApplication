package dev.chu.memo.ui.mvi

import android.util.Log
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar
import dev.chu.memo.R
import dev.chu.memo.databinding.ActivityMviBinding
import dev.chu.memo.entity.User
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.showToast
import dev.chu.memo.ui.mvi.aac.AacMviActivity
import dev.chu.memo.ui.rv_coroutine.UserAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

// 참고 : https://proandroiddev.com/best-architecture-for-android-mvi-livedata-viewmodel-71a3a5ac7ee3

class MviActivity : AacMviActivity<MviViewState, MviViewEffect, MviViewEvent, MviViewModel, ActivityMviBinding>() {
    // gradle에 activity.ktx 추가 시 발생하는 함수(viewModels)
    override val viewModel by viewModel<MviViewModel>()
    private val userAdapter by inject<UserAdapter>()

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_mvi

    override fun initView() {
        Log.i(TAG, "initView")
        binding.activity = this
        binding.adapter = userAdapter

        userAdapter.setCallback(object : UserAdapter.ACallback {
            override fun onClickEvent(item: User) {
                viewModel.process(MviViewEvent.UsersItemClicked(item))
            }
        })

        binding.srlUsersHome.setOnRefreshListener {
            viewModel.process(MviViewEvent.OnSwipeRefresh)
        }
    }

    override fun renderViewState(viewState: MviViewState) {
        when (viewState.fetchStatus) {
            is FetchStatus.Fetched -> {
                binding.srlUsersHome.isRefreshing = false
            }
            is FetchStatus.NotFetched -> {
                viewModel.process(MviViewEvent.FetchUsers)
                binding.srlUsersHome.isRefreshing = false
            }
            is FetchStatus.Fetching -> {
                binding.srlUsersHome.isRefreshing = true
            }
        }
        userAdapter.submitList(viewState.userList)
    }

    override fun renderViewEffect(viewEffect: MviViewEffect) {
        when (viewEffect) {
            is MviViewEffect.ShowSnackbar -> {
                Snackbar.make(binding.coordinatorLayoutRoot, viewEffect.message, Snackbar.LENGTH_SHORT).show()
            }
            is MviViewEffect.ShowToast -> {
                showToast(viewEffect.message)
            }
        }
    }

    fun onClickFab() {
        viewModel.process(MviViewEvent.FabClicked)
    }
}