package dev.chu.memo.view.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.chu.memo.R
import dev.chu.memo.common.Const
import dev.chu.memo.data.local.MemoData
import dev.chu.memo.databinding.ItemMainBinding
import dev.chu.memo.etc.extension.confirmDialog
import dev.chu.memo.view.activity.read.ReadActivity
import dev.chu.memo.view_model.RoomViewModel

class MainAdapter(
    val context: Context,
    private val items: MutableList<MemoData>,
    private val roomVm: RoomViewModel
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    fun setItems(item: List<MemoData>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = items[position]
        holder.bind(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemMainBinding>(view)!!

        fun bind(item : MemoData) {
            binding.container.setOnClickListener {
                context.startActivity(Intent(context, ReadActivity::class.java).apply {
                    putExtra(Const.EXTRA.MEMO_ID, item.memo_id)
                })
            }

            binding.container.setOnLongClickListener {
                context.confirmDialog(R.string.do_you_delete_this_memo, DialogInterface.OnClickListener { dialog, which -> roomVm.deleteMemo(item) })
                return@setOnLongClickListener false
            }
        }
    }
}