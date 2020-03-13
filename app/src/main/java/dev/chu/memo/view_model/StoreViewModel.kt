package dev.chu.memo.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseViewModel
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.data.repository.StoreRepository
import dev.chu.memo.data.response.StoreRes
import dev.chu.memo.data.response.StoresByGeoRes
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.with
import dev.chu.memo.etc.listener.DataListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel(private val repository: StoreRepository) : BaseViewModel() {

    private var _storeList: MutableLiveData<StoreRes> = MutableLiveData()
    val storeList: LiveData<StoreRes> get() = _storeList

    private var _storeByGeoList: MutableLiveData<StoresByGeoRes> = MutableLiveData()
    val storeByGeoList: LiveData<StoresByGeoRes> get() = _storeByGeoList

    fun getStore(perPage: Int) = addDisposable(repository.getStores(perPage, object : DataListener<StoreRes> {
        override fun onSuccess(t: StoreRes) {
            Log.i(TAG, "onSuccess t = $t")
            _storeList.value = t
        }
    }))

    fun getStoresByGeo(lat: Double, lng: Double, m: Int) =
        addDisposable(repository.getStoresByGeo(lat, lng, m, object : DataListener<StoresByGeoRes> {
            override fun onSuccess(t: StoresByGeoRes) {
                Log.i(TAG, "onSuccess t = $t")
                _storeByGeoList.value = t
            }
        }))
}