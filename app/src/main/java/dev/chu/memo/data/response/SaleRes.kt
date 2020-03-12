package dev.chu.memo.data.response

data class SaleRes(
    val count: Int,
    val page: Int,
    val sales: List<Sale>,
    val totalCount: Int,
    val totalPages: Int
)