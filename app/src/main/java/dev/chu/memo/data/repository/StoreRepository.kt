package dev.chu.memo.data.repository

import android.util.Log
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.entity.StoreRes
import dev.chu.memo.entity.StoresByGeoRes
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.with
import dev.chu.memo.etc.listener.DataListener

class StoreRepository(private val api: ApiService) {

    fun getStores(perPage: Int, listener: DataListener<StoreRes>) =
        api.getStores(page = (perPage / 500)+1, perPage = perPage)
            .with()
            .subscribe({
                Log.i(TAG, "onSuccess it = $it")
                listener.onSuccess(it)
            }, {
                Log.e(TAG, "onError = "+it.printStackTrace())
            })

    fun getStoresByGeo(lat: Double, lng: Double, m: Int, listener: DataListener<StoresByGeoRes>) =
        api.getStoresByGeo(lat, lng, m)
            .with()
            .subscribe({
                Log.i(TAG, "onSuccess it = $it")
                listener.onSuccess(it)
            }, {
                Log.e(TAG, "onError = "+it.printStackTrace())
                listener.onFail(it)
            })
}