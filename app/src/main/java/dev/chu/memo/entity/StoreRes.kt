package dev.chu.memo.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreRes(
    @SerializedName("count")
    val count: Int,
    @SerializedName("page")
    val page: String,
    @SerializedName("storeInfos")
    val storeInfos: List<StoreInfo>
) : Parcelable

@Parcelize
data class StoreInfo(
    @SerializedName("addr")
    val addr: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
): Parcelable