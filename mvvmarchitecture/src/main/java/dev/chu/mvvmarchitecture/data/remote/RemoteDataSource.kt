package dev.chu.mvvmarchitecture.data.remote

import android.util.Log
import dev.chu.extensions.TAG
import dev.chu.mvvmarchitecture.api.ApiService
import dev.chu.mvvmarchitecture.data.local.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(
    private val api: ApiService
) {

    fun fetchData(callback: (List<Model>?) -> Unit) {
        val call: Call<List<Model>> = api.getImages(10)
        call.enqueue(object : Callback<List<Model>> {
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Log.e(TAG, "onFailure: " + t.message)
            }
        })
    }
}