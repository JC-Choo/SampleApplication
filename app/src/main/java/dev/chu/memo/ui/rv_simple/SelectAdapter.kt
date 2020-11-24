package dev.chu.memo.ui.rv_simple

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R

class SelectAdapter : RecyclerView.Adapter<SelectAdapter.SelectViewHolder>() {
    var list: List<Int> = arrayListOf()

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        val number = list[position]
        holder.bind(number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_select, parent, false)
        return SelectViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long = position.toLong()

    class SelectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var text: TextView = view.findViewById(R.id.title)

        fun bind(value: Int) {
            text.text = value.toString()
        }

        // ViewHolder에서
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }
}

// ItemDetailsLookup 클래스
class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as SelectAdapter.SelectViewHolder)
                .getItemDetails()
        }
        return null
    }
}