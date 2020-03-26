package dev.chu.memo.ui.rx_activity.repos

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.RxBaseViewModelActivity
import dev.chu.memo.databinding.ActivityGithubReposBinding
import dev.chu.memo.etc.extension.TAG
import io.reactivex.rxkotlin.plusAssign
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class GithubReposActivity : RxBaseViewModelActivity<ActivityGithubReposBinding>() {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_github_repos

    override val viewModel: GithubReposViewModel by lazy {
        GithubReposViewModel(get())
    }

    private val adapter: GithubReposAdapter by inject()

    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this
        binding.viewModel = viewModel

        binding.recyclerView.adapter = this.adapter
        binding.refreshLayout.setOnRefreshListener {
            viewModel.searchGithubRepos()
        }

        binding.searchText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchGithubRepos(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        compositeDisposable += viewModel.loadingState.subscribe {
            if(it) showLoading() else hideLoading()
        }

        compositeDisposable += viewModel.reposState.subscribe {
            adapter.items = it
        }

        viewModel.onCreate()
    }


    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.refreshLayout.isRefreshing = false
    }
}