package dev.chu.rv_drag_to_reorder.utils

import android.view.View
import dev.chu.rv_drag_to_reorder.ItemModel

val Any.TAG: String
    get() = this::class.java.simpleName ?: this.toString()

fun View.click(block: (View) -> Unit) {
    this.setOnClickListener(block)
}

fun getList(): List<ItemModel> {
    val list = mutableListOf<ItemModel>()
    for (i in 1 .. 20) {
        list.add(ItemModel("Item $i"))
    }
    return list
}