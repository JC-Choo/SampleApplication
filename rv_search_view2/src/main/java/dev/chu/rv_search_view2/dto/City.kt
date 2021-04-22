package dev.chu.rv_search_view2.dto

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("city_name") val cityName: String,
    @SerializedName("city_code") val cityCode: String
)