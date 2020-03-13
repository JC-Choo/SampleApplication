package dev.chu.memo.data.response

data class StoreInfo(
    val addr: String,
    val code: String,
    val lat: Double,
    val lng: Double,
    val name: String,
    val type: String
)