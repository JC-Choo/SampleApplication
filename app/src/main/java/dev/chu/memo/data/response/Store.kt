package dev.chu.memo.data.response

data class Store(
    val addr: String,
    val code: String,
    val created_at: String,
    val lat: Double,
    val lng: Double,
    val name: String,
    val remain_stat: String? = null,
    val stock_at: String,
    val type: String
)