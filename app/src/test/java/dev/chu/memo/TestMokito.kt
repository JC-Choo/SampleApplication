package dev.chu.memo

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.chu.memo.common.Const
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.entity.GithubRepo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TestMokito {

    @Test
    fun test() {
        val api = Retrofit.Builder()
            .baseUrl(Const.URL_GITHUB)
            .client(OkHttpClient.Builder().apply {
                addInterceptor( Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                        .header("Authorization", "token ${Const.TOKEN}")
                    chain.proceed(requestBuilder.build())
                })
            }.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.searchRepos("RxJava")
            .map { it.asJsonObject?.getAsJsonArray("items") }
            .map {
                val type = object : TypeToken<List<GithubRepo>>() {}.type
                GsonBuilder().setLenient().create().fromJson(it, type) as List<GithubRepo>
            }
            .test()
            .awaitDone(4, TimeUnit.SECONDS)
            .assertValue {
                println("$it ")
                it.isNotEmpty()
            }
            .assertComplete()
    }

    @Test
    fun test2() {
        val api = Retrofit.Builder()
            .baseUrl(Const.URL_CORONA)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getStores(1, 100)
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertValue {
                println(it)
                it.storeInfos.isNotEmpty()
            }
            .assertComplete()
    }
}