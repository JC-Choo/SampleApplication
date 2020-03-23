package dev.chu.memo.ui.rx_activity.repos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.entity.GithubRepo
import dev.chu.memo.ui.rx_activity.repo.GithubRepoActivity
import kotlinx.android.synthetic.main.item_github_repo.view.*

class GithubReposAdapter : RecyclerView.Adapter<GithubReposAdapter.RepoViewHolder>() {

    var items: List<GithubRepo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_github_repo, parent, false)
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: GithubRepo) {
            with(itemView) {
                repoName.text = repo.name
                repoDescription.text = repo.description
                repoStar.setImageResource(
                    if (repo.star) R.drawable.baseline_star_24
                    else R.drawable.baseline_star_border_24
                )
                setOnClickListener {
                    GithubRepoActivity.start(context, repo)
                }
            }
        }
    }
}
