package dev.chu.memo.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaleRes(
    @SerializedName("count")
    val count: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("sales")
    val sales: List<Sale>,
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("totalPages")
    val totalPages: Int
): Parcelable

@Parcelize
data class Sale(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("remain_stat")
    val remain_stat: String,
    @SerializedName("stock_at")
    val stock_at: String
): Parcelable