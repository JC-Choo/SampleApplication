package dev.chu.memo.view_model

import android.util.Log
import dev.chu.memo.base.BaseViewModel
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.with

class StoreViewModel(private val api: ApiService) : BaseViewModel() {
    fun getStore(perPage: Int) = addDisposable(api.getStores(page = perPage / 500, perPage = perPage)
        .with()
        .subscribe({
            Log.i(TAG, "onSuccess it = $it")
        }, {
            Log.e(TAG, "onError = "+it.printStackTrace())
        }))
}