package dev.chu.memo.view_model

import android.util.Log
import dev.chu.memo.base.BaseViewModel
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.data.repository.StoreRepository
import dev.chu.memo.data.response.StoreRes
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.with
import dev.chu.memo.etc.listener.DataListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel(private val repository: StoreRepository) : BaseViewModel() {
    fun getStore(perPage: Int) = addDisposable(repository.getStores(perPage, object : DataListener<StoreRes> {
        override fun onSuccess(t: StoreRes) {
            Log.i(TAG, "onSuccess t = $t")
        }
    }))
}