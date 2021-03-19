package dev.chu.mvvmarchitecture.api

import dev.chu.mvvmarchitecture.data.local.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/images/search")
    fun getImages(@Query("limit") limit: Int): Call<List<Model>>
}