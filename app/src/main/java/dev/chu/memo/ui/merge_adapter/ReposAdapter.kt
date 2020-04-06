package dev.chu.memo.ui.merge_adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.databinding.ItemRepoViewBinding
import dev.chu.memo.entity.Repo

class ReposAdapter : ListAdapter<Repo, ReposAdapter.RepoViewHolder>(REPO_COMPARATOR) {
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem === newItem
//                oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id
//                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposAdapter.RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoViewBinding.inflate(inflater)

        return RepoViewHolder(binding)

//        return RepoViewHolder.create(parent)
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun onBindViewHolder(holder: ReposAdapter.RepoViewHolder, position: Int) {
        val repoItem = getItem(position)
//        if(repoItem != null) {
//            holder.bind(repoItem)
            holder.binding.item = repoItem
            holder.binding.executePendingBindings()
//        }
        holder.bind(repoItem)
    }

    inner class RepoViewHolder(val binding: ItemRepoViewBinding): RecyclerView.ViewHolder(binding.root) {

        //    companion object {
//        fun create(parent: ViewGroup): RepoViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_repo_view, parent, false)
//            return RepoViewHolder(view)
//        }
//    }
//        private val view = binding.root
//        private val name: TextView = view.findViewById(R.id.repo_name)
//        private val description: TextView = view.findViewById(R.id.repo_description)
//        private val stars: TextView = view.findViewById(R.id.repo_stars)
//        private val language: TextView = view.findViewById(R.id.repo_language)
//        private val forks: TextView = view.findViewById(R.id.repo_forks)
//
//        private var repo: Repo? = null
//
//        init {
//            view.setOnClickListener {
//                repo?.url?.let { url ->
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    view.context.startActivity(intent)
//                }
//            }
//        }
//
        fun bind(repo: Repo?) {
            binding.root.setOnClickListener {
                repo?.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    binding.root.context.startActivity(intent)
                }
            }
//            if (repo == null) {
//                val resources = itemView.resources
////            name.text = resources.getString(R.string.loading)
//                description.visibility = View.GONE
//                language.visibility = View.GONE
//                stars.text = resources.getString(R.string.unknown)
//                forks.text = resources.getString(R.string.unknown)
//            } else {
//                showRepoData(repo)
//            }
        }
//
//        private fun showRepoData(repo: Repo) {
//            this.repo = repo
//            name.text = repo.fullName
//
//            // if the description is missing, hide the TextView
//            var descriptionVisibility = View.GONE
//            if (repo.description != null) {
//                description.text = repo.description
//                descriptionVisibility = View.VISIBLE
//            }
//            description.visibility = descriptionVisibility
//
//            stars.text = repo.stars.toString()
//            forks.text = repo.forks.toString()
//
//            // if the language is missing, hide the label and the value
//            var languageVisibility = View.GONE
//            if (!repo.language.isNullOrEmpty()) {
//                val resources = this.itemView.context.resources
//                language.text = resources.getString(R.string.language, repo.language)
//                languageVisibility = View.VISIBLE
//            }
//            language.visibility = languageVisibility
//        }
    }
}