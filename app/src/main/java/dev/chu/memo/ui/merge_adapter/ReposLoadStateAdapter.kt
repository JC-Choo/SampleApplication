package dev.chu.memo.ui.merge_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ReposLoadStateAdapter(private val retry: () -> Unit) : RecyclerView.Adapter<ReposLoadStateViewHolder>() {

    /**
     * LoadState to present in the adapter.
     *
     * Changing this property will immediately notify the Adapter to change the item it's
     * presenting.
     */
    var loadState: LoadState = LoadState.Done
        set(loadState) {
            if (field != loadState) {
                val displayOldItem = displayLoadStateAsItem(field)
                val displayNewItem = displayLoadStateAsItem(loadState)

                if (displayOldItem && !displayNewItem) {
                    notifyItemRemoved(0)
                } else if (displayNewItem && !displayOldItem) {
                    notifyItemInserted(0)
                } else if (displayOldItem && displayNewItem) {
                    notifyItemChanged(0)
                }
                field = loadState
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposLoadStateViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repos_load_state_header_view, parent, false)
//
//        return ReposLoadStateViewHolder(view, retry)
        return ReposLoadStateViewHolder.create(parent, retry)
    }

    override fun getItemViewType(position: Int): Int = 0

    override fun getItemCount(): Int = if (displayLoadStateAsItem(loadState)) 1 else 0

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, position: Int) {
        holder.bind(loadState)
    }

    /**
     * Returns true if the LoadState should be displayed as a list item when active.
     *
     *  [LoadState.Loading] and [LoadState.Error] present as list items,
     * [LoadState.Done] is not.
     */
    private fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error
    }
}