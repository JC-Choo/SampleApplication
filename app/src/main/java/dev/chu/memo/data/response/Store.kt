package dev.chu.memo.data.response

data class Store(
    val addr: String,
    val code: String,
    val created_at: String,
    val lat: Int,
    val lng: Int,
    val name: String,
    val remain_stat: String,
    val stock_at: String,
    val type: String
)