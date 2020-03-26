package dev.chu.memo.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoresByAddrRes(
    @SerializedName("count")
    val count: Int,
    @SerializedName("stores")
    val stores: List<StoreByAddr>
): Parcelable

@Parcelize
data class StoreByAddr(
    @SerializedName("addr")
    val addr: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("lat")
    val lat: Int,
    @SerializedName("lng")
    val lng: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("remain_stat")
    val remain_stat: String,
    @SerializedName("stock_at")
    val stock_at: String,
    @SerializedName("type")
    val type: String
): Parcelable