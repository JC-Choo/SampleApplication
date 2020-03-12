package dev.chu.memo.data.response

data class StoreRes(
    val count: Int,
    val page: String,
    val storeInfos: List<StoreInfo>
)