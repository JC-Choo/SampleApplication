package dev.chu.memo.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.chu.memo.common.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Api {
    private const val TIMEOUT_MS = 10000L

    private val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, BooleanTypeConverter())
            .create()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        val builder = OkHttpClient.Builder()

        val httpLoggingInterceptor = HttpLoggingInterceptor()       // Retrofit 에서 통신 과정의 로그를 확인하기 위함. 로그의 level 을 지정
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(httpLoggingInterceptor)

        val okHttpClient = builder
            .connectTimeout(TIMEOUT_MS, TimeUnit.SECONDS)       // 요청을 시작한 후 서버와의 TCP handshake가 완료되기까지 지속되는 시간. 즉, Retrofit이 설정된 연결 시간 제한 내에서 서버에 연결할 수 없는 경우 해당 요청을 실패한 것으로 계산.
            .readTimeout(TIMEOUT_MS, TimeUnit.SECONDS)          // 읽기 시간 초과는 연결이 설정되면 모든 바이트가 전송되는 속도를 감시. 서버로부터의 응답까지의 시간이 읽기 시간 초과보다 크면 요청이 실패로 계산.
            .writeTimeout(TIMEOUT_MS, TimeUnit.SECONDS)         // 읽기 타임 아웃의 반대 방향, 얼마나 빨리 서버에 바이트를 보낼 수 있는지 확인.
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(serviceClass)
    }
}