package dev.chu.memo.di_koin

import com.google.gson.GsonBuilder
import dev.chu.memo.BuildConfig
import dev.chu.memo.common.Const
import dev.chu.memo.data.local.MemoDatabase
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.data.remote.BooleanTypeConverter
import dev.chu.memo.data.remote.NullOrEmptyConverterFactory
import dev.chu.memo.data.repository.RoomRepository
import dev.chu.memo.data.repository.StoreRepository
import dev.chu.memo.view.adapter.ImageAdapter
import dev.chu.memo.view.adapter.ImageModifyAdapter
import dev.chu.memo.view_model.RoomViewModel
import dev.chu.memo.view_model.StoreViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Koin은 크게 아래 단계를 통해서 구현을 하게 됩니다.
 * 1. 모듈 생성하기 (Koin DSL)
 * 2. Android Application Class 에서 startKoin()으로 실행하기
 * 3. 의존성 주입
 *
 * module 이란? 의존성 주입을 하는 실제 코드, 혹은 의존성 주입을 위한 설계도
 * DSL 이란? Domain Specific Language 로, 도메인 특화 언어, 즉, "특정 도메인을 적용하는데 특화된 언어"를 말합니다.
 *
 * Koin DSL 은 아래와 같다.
 * applicationContext : Koin 모듈 생성
 * factory : 매번 inject 할때마다 인스턴스 생성 -> 호출할 때마다 매번 객체를 생생해 사용
 * single : 싱글톤 생성 -> 한번 생성 후 메모리에서 계속 가지고 있음
 * bind : 종속시킬 class 나 interface 를 주입
 * get : 컴포넌트내에서 알맞은 의존성을 주입함
 */

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply { if(BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get() as HttpLoggingInterceptor)        // Retrofit 에서 통신 과정의 로그를 확인하기 위함. 로그의 level 을 지정
            .connectTimeout(10000L, TimeUnit.SECONDS)       // 요청을 시작한 후 서버와의 TCP handshake가 완료되기까지 지속되는 시간. 즉, Retrofit이 설정된 연결 시간 제한 내에서 서버에 연결할 수 없는 경우 해당 요청을 실패한 것으로 계산.
            .readTimeout(10000L, TimeUnit.SECONDS)          // 읽기 시간 초과는 연결이 설정되면 모든 바이트가 전송되는 속도를 감시. 서버로부터의 응답까지의 시간이 읽기 시간 초과보다 크면 요청이 실패로 계산.
            .writeTimeout(10000L, TimeUnit.SECONDS)         // 읽기 타임 아웃의 반대 방향, 얼마나 빨리 서버에 바이트를 보낼 수 있는지 확인.
            .build()
    }

    single {
        GsonBuilder()
            .registerTypeAdapter(Boolean::class.java,
                BooleanTypeConverter()
            )
            .create()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single {
        Interceptor { chain ->
            chain.proceed(chain.request().newBuilder().apply {
                header("Accept", "application/json")
            }.build())
        }
    }
}

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(ApiService::class.java) }
}

val roomModule = module {
    factory { MemoDatabase.getInstance(androidApplication()).getMemoDao() }
}

val repositoryModule = module {
    factory { RoomRepository(get()) }
    factory { StoreRepository(get()) }
}

val viewModelModule = module {
    viewModel { RoomViewModel(get()) }
    viewModel { StoreViewModel(get()) }
}

val adapterModule = module {
    factory { ImageAdapter(mutableListOf()) }
    factory { ImageModifyAdapter(mutableListOf()) }
}

val myDiModule = listOf(networkModule, apiModule, roomModule, repositoryModule, viewModelModule, adapterModule)