package dev.chu.memo.data

data class ItemMemo(
    val title: String,
    val content: String,
    val imageUrl: String = ""
)