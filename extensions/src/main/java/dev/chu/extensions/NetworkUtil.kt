package dev.chu.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * 인터넷 연결의 가능 또는 불가능을 검출하는 네트워크 유틸리티
 */
object NetworkUtil {
    private val networkLiveData = MutableLiveData(false)

    /**
     * Returns instance of [LiveData] which can be observed for network changes.
     */
    fun getNetworkLiveData(ctx: Context): LiveData<Boolean> {
        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkLiveData.postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkLiveData.postValue(false)
            }
        }

        if (isAndroid24More()) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }

        return networkLiveData
    }
}