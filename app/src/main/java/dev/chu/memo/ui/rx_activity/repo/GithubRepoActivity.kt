package dev.chu.memo.ui.rx_activity.repo

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import dev.chu.memo.R
import dev.chu.memo.base.RxBaseActivity
import dev.chu.memo.databinding.ActivityGithubRepoBinding
import dev.chu.memo.entity.GithubRepo
import dev.chu.memo.etc.extension.TAG
import io.reactivex.rxkotlin.plusAssign
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class GithubRepoActivity: RxBaseActivity<ActivityGithubRepoBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_github_repo

    companion object {
        private const val KEY_REPO = "KEY_REPO"

        fun start(context: Context, repo: GithubRepo) {
            context.run {
                startActivity(
                    Intent(this, GithubRepoActivity::class.java)
                        .putExtra(KEY_REPO, repo)
                )
            }
        }
    }

    private val viewModel by lazy { GithubRepoViewModel(get()) }
    private val issuesAdapter: IssuesAdapter by inject()

    override fun initView() {
        Log.i(TAG, "initView")

        binding.activity = this
        binding.issues.adapter = issuesAdapter

        compositeDisposable += viewModel.repoState.subscribe {
            binding.repo = it
            viewModel.save(it)
        }
        compositeDisposable += viewModel.issuesState.subscribe {
            binding.issueLabel.visibility = View.VISIBLE
            issuesAdapter.items = it.toMutableList()
        }

        intent.getParcelableExtra<GithubRepo>(KEY_REPO)?.let {
            supportActionBar?.run {
                title = it.name
                setDisplayHomeAsUpEnabled(true)
            }
            viewModel.onCreate(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_repo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.create_issue -> {
//                IssueCreateActivity.start(this, viewModel.repoState.value!!)
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    fun onClickStart() { viewModel.onClickStar() }

    fun onClickOwner() {
//        UserActivity.start(this, viewModel.repoState.value!!.owner)
    }
}