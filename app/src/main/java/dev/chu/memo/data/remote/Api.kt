//package dev.chu.memo.data.remote
//
//import com.google.gson.GsonBuilder
//import dev.chu.memo.common.Const
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
///**
// * okHttpClient의 3가지 타임 아웃
// * 1. connectTimeout
// * - 요청을 시작한 후 서버와의 TCP handshake가 완료되기까지 지속되는 시간
// * - 설정된 connectionTimeout 제한 내 서버에 연결할 수 없다면, 해당 요청을 실패한 것으로 계산
// * - 예를 들어, 사용자의 인터넷 연결 상태가 좋지 않다면, 기본 시간 제한인 10초를 더 높은 값으로 설정하면 좋음
// *
// * 2. readTimeout
// * - 연결이 설정될 때 주의를 기울이 시작하고 그때 모든 바이트가 얼마나 빠르게 혹은 느리게 전송되는지 본다.
// * - 두 바이트들 사이에 시간이 readTimeout 보다 커지면, 요청은 실패로서 카운트된다.
// * - 카운터는 들어오는 바이트 마다 리셋된다.
// * - 그러므로, 만약 응답이 120바이트를 가지고 각 바이트들 사이에 5초가 걸린다면, readTimeout 은 트리거되지 않을 것이고 응답은 완료하는데 10분이 걸릴 것이다.
// * - 다시 말하면, 이건 단지 서버에 제한된거 뿐만 아니다. 느린 readTimeout 은 인터넷 접속에 의해 야기될수도 있다.
// *
// * - 연결이 설정되면 모든 바이트가 전송되는 속도를 감시
// * - 서버로부터의 응답까지의 시간 > 읽기 시간 초가 => 요청 실패
// * - LongPolling을 위해 변경해주어야 하는 설정값
// *
// * 3. writeTimeout
// * - 읽기 타임 아웃의 반대 방향, 얼마나 빨리 서버에 바이트를 보낼 수 있는지 확인
// * - 만약 단일 바이트를 보내는데 구성된 writeTimeout 보다 더 길게 걸린다면, 레트로핏은 요청을 실패로서 카운트 할 것이다.
// */
//
//object Api {
//    private const val TIMEOUT_MS = 10000L
//
////    private val gson: Gson by lazy {
////        GsonBuilder()
////            .registerTypeAdapter(Boolean::class.java, BooleanTypeConverter())
////            .create()
////    }
//
//    val gson = GsonBuilder().setLenient().create()
//
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })   // Retrofit 에서 통신 과정의 로그를 확인하기 위함. 로그의 level 을 지정
//        .addInterceptor { chain ->
//            val requestBuilder = chain.request().newBuilder()
//                .header("Authorization", "token ${Const.TOKEN}")
//            chain.proceed(requestBuilder.build())
//        }
////        .connectTimeout(TIMEOUT_MS, TimeUnit.SECONDS)       // 요청을 시작한 후 서버와의 TCP handshake가 완료되기까지 지속되는 시간. 즉, Retrofit이 설정된 연결 시간 제한 내에서 서버에 연결할 수 없는 경우 해당 요청을 실패한 것으로 계산.
////        .readTimeout(TIMEOUT_MS, TimeUnit.SECONDS)          // 읽기 시간 초과는 연결이 설정되면 모든 바이트가 전송되는 속도를 감시. 서버로부터의 응답까지의 시간이 읽기 시간 초과보다 크면 요청이 실패로 계산.
////        .writeTimeout(TIMEOUT_MS, TimeUnit.SECONDS)         // 읽기 타임 아웃의 반대 방향, 얼마나 빨리 서버에 바이트를 보낼 수 있는지 확인.
//        .build()
//
////    private val githubAdapter by lazy {
////        Retrofit.Builder()
////            .baseUrl(Const.URL_GITHUB)
////            .client(okHttpClient)
////            .addConverterFactory(GsonConverterFactory.create(gson))
////            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
////            .build()
////    }
////
////    val API_GITHUB_SERVICE: ApiService by lazy {
////        githubAdapter.create(ApiService::class.java)
////    }
//
//    fun <T> createService(serviceClass: Class<T>): T {
//
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(Const.URL_GITHUB)
//            .addConverterFactory(NullOrEmptyConverterFactory())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(serviceClass)
//    }
//}