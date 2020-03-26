package dev.chu.memo.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseViewModel
import dev.chu.memo.data.repository.StoreRepository
import dev.chu.memo.entity.StoreRes
import dev.chu.memo.entity.StoresByGeoRes
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.listener.DataListener

class CoronaViewModel(private val repository: StoreRepository) : BaseViewModel() {

    private var _storeList: MutableLiveData<StoreRes> = MutableLiveData()
    val storeList: LiveData<StoreRes> get() = _storeList

    private var _storeByGeoList: MutableLiveData<StoresByGeoRes> = MutableLiveData()
    val storeByGeoList: LiveData<StoresByGeoRes> get() = _storeByGeoList

    private var _refresh: MutableLiveData<Boolean> = MutableLiveData(false)
    val refresh: LiveData<Boolean>
        get() = _refresh

    fun getStore(perPage: Int) = addDisposable(repository.getStores(perPage, object : DataListener<StoreRes> {
        override fun onSuccess(t: StoreRes) {
            Log.i(TAG, "onSuccess t = $t")
            _storeList.value = t
        }

        override fun onFail(e: Throwable) {

        }
    }))

    fun getStoresByGeo(lat: Double, lng: Double, m: Int) {
        _refresh.value = true
        addDisposable(repository.getStoresByGeo(lat, lng, m, object : DataListener<StoresByGeoRes> {
            override fun onSuccess(t: StoresByGeoRes) {
                Log.i(TAG, "onSuccess t = $t")
                _storeByGeoList.value = t
                _refresh.value = false
            }

            override fun onFail(e: Throwable) {
                _refresh.value = false
            }
        }))
    }
}