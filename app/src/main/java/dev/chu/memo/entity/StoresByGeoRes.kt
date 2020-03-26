package dev.chu.memo.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoresByGeoRes(
    @SerializedName("count")
    val count: Int,
    @SerializedName("stores")
    val stores: List<StoreByGeo>
): Parcelable

@Parcelize
data class StoreByGeo(
    @SerializedName("addr")
    val addr: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("remain_stat")
    val remain_stat: String? = null,
    @SerializedName("stock_at")
    val stock_at: String,
    @SerializedName("type")
    val type: String
): Parcelable