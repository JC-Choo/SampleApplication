package dev.chu.memo.ui.base_rv_test

import dev.chu.basemodule.recycler_view.BaseItemModel

data class ItemModel(
    val itemId: String
): BaseItemModel(itemId) {
    var name: String? = null
}