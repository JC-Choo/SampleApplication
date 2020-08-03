package dev.chu.memo.ui.rv_coroutine

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityUserBinding
import dev.chu.memo.etc.extension.TAG
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

// 참고 : https://medium.com/swlh/android-recyclerview-with-data-binding-and-coroutine-3192097a0496

class UserActivity : BaseActivity<ActivityUserBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_user

    private val userVM by viewModel<UserViewModel>()
    private val userAdapter by inject<UserAdapter>()

    override fun initView(savedInstanceState: Bundle?) {
        Log.i(TAG, "initView")

        binding.adapter = userAdapter
        binding.viewModel = userVM

        observeViewModel()
    }

    private fun observeViewModel() {
        userVM.data.observe(this, Observer {
            Log.e(TAG, it.toString())
            it.let(userAdapter::submitList)
        })
    }
}