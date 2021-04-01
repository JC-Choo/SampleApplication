package dev.chu.rv_checkbox_selection

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.chu.extensions.dpToPixel
import dev.chu.extensions.getColorById
import dev.chu.rv_checkbox_selection.databinding.ItemCheckboxBinding

class CheckBoxAdapter(
    private val context: Context,
    var productList: List<RowModel>,
) : RecyclerView.Adapter<CheckBoxAdapter.TableViewHolder>() {

    companion object {
        const val TOP_HEADER: String = "All Products"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCheckboxBinding.inflate(layoutInflater, parent, false)
        return TableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val item = productList[position]

        holder.binding.productCheckbox.setOnCheckedChangeListener(null)
        holder.binding.productCheckbox.isChecked = item.isChecked
        setCheckboxTextColor(item.isChecked, holder)

        val params = holder.binding.productCheckbox.layoutParams as ViewGroup.MarginLayoutParams

        when (item.rowType) {
            RowType.TopHeader -> {
                holder.binding.productCheckbox.text = TOP_HEADER
                holder.binding.productCheckbox.typeface = Typeface.DEFAULT_BOLD
                params.setMargins(0, 0, 0, 0)
                holder.binding.productCheckbox.layoutParams = params
            }

            RowType.CatHeader -> {
                holder.binding.productCheckbox.text = item.category
                holder.binding.productCheckbox.typeface = Typeface.DEFAULT_BOLD
                params.setMargins(0, 0, 0, 0)
                holder.binding.productCheckbox.layoutParams = params
            }

            RowType.ProductRow -> {
                holder.binding.productCheckbox.text = item.productName
                holder.binding.productCheckbox.typeface = Typeface.DEFAULT
                params.setMargins(context.dpToPixel(20f).toInt(), 0, 0, 0)
                holder.binding.productCheckbox.layoutParams = params
            }
        }

        holder.binding.productCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (item.isChecked != isChecked) {
                setCheckboxTextColor(isChecked, holder)
                item.isChecked = isChecked

                when (item.rowType) {
                    RowType.TopHeader -> {
                        val indexList = mutableListOf<Int>()
                        productList.filter {
                            it.rowType != RowType.TopHeader
                        }.forEach {
                            it.isChecked = isChecked
                            indexList.add(productList.indexOf(it))
                        }
                        indexList.forEach {
                            notifyItemChanged(it)
                        }
                    }

                    RowType.CatHeader -> {
                        val indexList = mutableListOf<Int>()
                        productList.filter {
                            it.rowType == RowType.ProductRow && it.category == item.category
                        }.forEach {
                            it.isChecked = isChecked
                            indexList.add(productList.indexOf(it))
                        }
                        indexList.forEach {
                            notifyItemChanged(it)
                        }
                        isAllItemsSameStatus() //for header
                    }

                    RowType.ProductRow -> {
                        isAllItemsSameStatus(item.category) //set prep area accordingly
                        isAllItemsSameStatus() //set top header
                    }
                }
            }
        }
    }

    override fun getItemCount() = productList.size

    private fun setCheckboxTextColor(isChecked: Boolean, holder: TableViewHolder) {
        if (isChecked) {
            holder.binding.productCheckbox.setTextColor(context.getColorById(R.color.black))
        } else {
            holder.binding.productCheckbox.setTextColor(context.getColorById(R.color.gray))
        }
    }

    private fun isAllItemsSameStatus(cat: String? = null) {
        val row: RowModel
        var isChecked = true
        var position = 0

        if (cat != null) {
            val catRow = productList.find { it.rowType == RowType.CatHeader && it.category == cat }
            catRow?.let { model ->
                val subList = productList.filter {
                    it.category == model.category && it.rowType == RowType.ProductRow
                }
                isChecked = subList.filter { it.isChecked }.size == subList.size
                position = productList.indexOf(catRow)
            }
            if (catRow == null)
                return
            else
                row = catRow
        } else {
            row = productList[0]
            isChecked = productList.filter { model ->
                model.rowType != RowType.TopHeader && model.isChecked
            }.size == productList.size - 1
            position = 0
        }

        updateHeader(row, isChecked, position)
    }


    private fun updateHeader(item: RowModel, isChecked: Boolean, position: Int) {
        // no need to update if no change
        if (item.isChecked != isChecked) {
            item.isChecked = isChecked
            notifyItemChanged(position)
        }
    }

    class TableViewHolder(
        val binding: ItemCheckboxBinding
    ) : RecyclerView.ViewHolder(binding.root)
}