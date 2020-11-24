package dev.chu.memo.ui.rx_activity.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.base.BaseViewHolder
import dev.chu.memo.databinding.ItemIssueBinding
import dev.chu.memo.entity.Issue

class IssuesAdapter : RecyclerView.Adapter<IssuesAdapter.IssueViewHolder>() {

    var items: MutableList<Issue> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder =
        IssueViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_issue, parent, false
            )
        )

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.binding.issue = items[position]
    }

    override fun getItemCount(): Int = items.size

    inner class IssueViewHolder(view: View): BaseViewHolder<ItemIssueBinding>(view)

}